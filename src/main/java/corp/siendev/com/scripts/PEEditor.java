/**
 * Java ON Conf - Malware analysis tool
 * Copyright 2026
 **/

package corp.siendev.com.scripts;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PEEditor {
    public static void main(String[] args) {
        File targetFile = new File("file_name.exe");
        String sectionName = ".newSection";
        int payloadSize = 512; // Size of your payload or empty space
        int characteristics = 0xE0000020; // IMAGE_SCN_CODE | MEM_EXECUTE | MEM_READ | MEM_WRITE

        try {
            addSection(targetFile, sectionName, payloadSize, characteristics);
            System.out.println("Successfully added section " + sectionName + " to the PE file.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addSection(File file, String name, int size, int characteristics) throws Exception {
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            // 1. Verify DOS Header and locate PE Signature offset
            raf.seek(0);
            if (raf.readShort() != 0x5A4D) { // "MZ"
                throw new IllegalArgumentException("Not a valid DOS header.");
            }
            raf.seek(0x3C);
            int peOffset = Integer.reverseBytes(raf.readInt());

            // 2. Verify PE Signature
            raf.seek(peOffset);
            if (raf.readInt() != 0x00004550) { // "PE\0\0"
                throw new IllegalArgumentException("Not a valid PE header.");
            }

            // 3. Read and increment Number Of Sections
            int fileHeaderOffset = peOffset + 4;
            raf.seek(fileHeaderOffset + 2); // Skip Machine Type
            short numSections = Short.reverseBytes(raf.readShort());

            raf.seek(fileHeaderOffset + 2);
            raf.writeShort(Short.reverseBytes((short) (numSections + 1)));

            // 4. Read SizeOfOptionalHeader and Magic to differentiate PE32 / PE32+
            raf.seek(fileHeaderOffset + 16);
            short sizeOfOptionalHeader = Short.reverseBytes(raf.readShort());

            int optionalHeaderOffset = fileHeaderOffset + 20;
            raf.seek(optionalHeaderOffset);
            short magic = Short.reverseBytes(raf.readShort());

            boolean is64Bit = (magic == 0x20B); // 0x10B = PE32, 0x20B = PE32+

            // 5. Read Alignments and SizeOfImage from Optional Header
            raf.seek(optionalHeaderOffset + 32);
            int sectionAlignment = Integer.reverseBytes(raf.readInt());
            int fileAlignment = Integer.reverseBytes(raf.readInt());

            int sizeOfImageOffset = optionalHeaderOffset + (is64Bit ? 56 : 56);
            raf.seek(sizeOfImageOffset);
            int sizeOfImage = Integer.reverseBytes(raf.readInt());

            // 6. Navigate to Section Table (starts directly after Optional Header)
            int sectionTableOffset = optionalHeaderOffset + sizeOfOptionalHeader;
            int lastSectionHeaderOffset = sectionTableOffset + (numSections - 1) * 40;

            // Read metrics from the current last section to calculate new offsets
            raf.seek(lastSectionHeaderOffset + 8); // Skip Name
            int lastVirtualSize = Integer.reverseBytes(raf.readInt());
            int lastVirtualAddress = Integer.reverseBytes(raf.readInt());
            int lastRawSize = Integer.reverseBytes(raf.readInt());
            int lastRawOffset = Integer.reverseBytes(raf.readInt());

            // 7. Calculate aligned values for the new section
            int newVirtualAddress = alignUp(lastVirtualAddress + alignUp(lastVirtualSize, sectionAlignment), sectionAlignment);
            int newRawOffset = alignUp(lastRawOffset + lastRawSize, fileAlignment);
            int newRawSize = alignUp(size, fileAlignment);
            int newSizeOfImage = alignUp(newVirtualAddress + newVirtualAddress, sectionAlignment); // Simplified boundary calculation

            // 8. Update SizeOfImage in the Optional Header
            raf.seek(sizeOfImageOffset);
            raf.writeInt(Integer.reverseBytes(alignUp(newVirtualAddress + newVirtualAddress, sectionAlignment)));

            // 9. Write the new Section Header Entry (40 bytes)
            int newSectionHeaderOffset = sectionTableOffset + numSections * 40;

            // Safety Check: Verify there is enough empty padding space before code sections start
            if (newSectionHeaderOffset + 40 > newVirtualAddress) {
                throw new IllegalStateException("No available space in PE headers to append a new section header.");
            }

            raf.seek(newSectionHeaderOffset);

            // Name (8 bytes) padded with null bytes
            byte[] nameBytes = new byte[8];
            System.arraycopy(name.getBytes(), 0, nameBytes, 0, Math.min(name.length(), 8));
            raf.write(nameBytes);

            // Structural Dimensions
            raf.writeInt(Integer.reverseBytes(size));                // VirtualSize
            raf.writeInt(Integer.reverseBytes(newVirtualAddress));   // VirtualAddress
            raf.writeInt(Integer.reverseBytes(newRawSize));          // SizeOfRawData
            raf.writeInt(Integer.reverseBytes(newRawOffset));         // PointerToRawData
            raf.writeInt(0);                                         // PointerToRelocations
            raf.writeInt(0);                                         // PointerToLinenumbers
            raf.writeShort(0);                                       // NumberOfRelocations
            raf.writeShort(0);                                       // NumberOfLinenumbers
            raf.writeInt(Integer.reverseBytes(characteristics));    // Characteristics

            // 10. Append Null padding/Payload bytes to the end of the file
            raf.setLength(newRawOffset + newRawSize);
            raf.seek(newRawOffset);
            byte[] emptyPayload = new byte[newRawSize]; // Populated with zeros
            raf.write(emptyPayload);
        }
    }

    private static int alignUp(int value, int alignment) {
        return (value + alignment - 1) & ~(alignment - 1);
    }
}

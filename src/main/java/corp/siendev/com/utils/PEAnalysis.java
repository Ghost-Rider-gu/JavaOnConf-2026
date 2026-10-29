/**
 * Java ON Conf - Malware analysis tool
 * Copyright 2026
 **/

package corp.siendev.com.utils;

import io.github.struppigel.parser.PEData;
import io.github.struppigel.parser.PELoader;
import io.github.struppigel.parser.sections.SectionTable;
import io.github.struppigel.parser.sections.edata.ExportEntry;
import io.github.struppigel.parser.sections.idata.ImportDLL;
import io.github.struppigel.tools.sigscanner.PEiDImporter;
import io.github.struppigel.tools.sigscanner.SignatureDatabase;
import io.github.struppigel.tools.sigscanner.SignatureMatch;
import io.github.struppigel.tools.sigscanner.SignatureScanner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PEAnalysis {
    private static PEData peData;

    public PEAnalysis(String peFileName) {
        if (peFileName.isBlank()) {
            throw new RuntimeException("PE file name shouldn't be empty");
        }
        peData = loadPE(peFileName);
    }

    private PEData loadPE(String peFileName) {
        try {
            return PELoader.loadPE(new File(peFileName));
        } catch (IOException ex) {
            throw new RuntimeException("Unable to load PE file: ", ex);
        } catch (IllegalStateException ex) {
            throw new RuntimeException("Not valid PE file: ", ex);
        }
    }

    public List<String> getSignatureDetails() {
        SignatureDatabase peId = PEiDImporter.load(new File(Thread.currentThread().getContextClassLoader().getResource("userdb.txt").getPath()));
        SignatureScanner scanner = new SignatureScanner(peId);
        List<SignatureMatch> matches = scanner.scan(peData);

        List<String> signatureData = new ArrayList<>(matches.size());
        matches.forEach(signature -> signatureData.add(signature.getName()));
        return signatureData;
    }

    public SectionTable getSections() {
        return peData.getSectionTable();
    }

    public List<ImportDLL> getImports() {
        if (peData.hasImports()) {
            return peData.loadImports();
        } else {
            return Collections.emptyList();
        }
    }

    public List<ExportEntry> getExports() {
        if (peData.hasExports()) {
            return peData.loadExports();
        } else {
            return Collections.emptyList();
        }
    }
}

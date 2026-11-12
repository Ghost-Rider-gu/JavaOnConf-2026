#include "stdafx.h"
#include "windows.h"

// adding new section into PE Sections
#pragma data_seg(".new_section")
char userName[] = "user";
char userPassword[] = "password";

// back to previous section (.data or .rdata)
#pragma data_seg()
char userAdmin[] = "Super Admin";
char adminPassword[] = "12345";

int _tmain(int argc, _TCHAR* argv[]) {
	char bufferUserName[50];
	char bufferPassword[20];

	printf("Input your name: > ");
	fgets(&bufferUserName[0], 50, stdin);

    printf("Input your password: > ");
	fgets(&bufferPassword[0], 20, stdin);

	if ((strcmp(&bufferUserName[0], userName)) & (strcmp(&bufferPassword[0], userPassword)))
		printf("Access granted");
	else
        printf("Access denied");

	return 0;
}

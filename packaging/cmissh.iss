;
; $Id: opennx.iss 643 2011-06-23 12:07:58Z felfert $
;
#undef DEBUG

#define APPNAME "CMIS Shell"
#include "version.iss"

#define APPIDSTR "{65961233-5A2D-46AE-BCC2-8EE700A0DDCA}"
#define APPIDVAL "{" + APPIDSTR

[Setup]
AppName={#=APPNAME}
AppVersion={#=APPVERSION}
AppVerName={#=APPVERNAME}
ArchitecturesInstallIn64BitMode=x64 ia64
AppPublisher=GRAU DATA AG
AppPublisherURL=http://www.graudata.com
AppCopyright=(C) 2013 GRAU DATA AG
VersionInfoVersion={#=APPVERSION}
DefaultDirName={pf}\{#=APPNAME}
DefaultGroupName={#=APPNAME}
#ifdef DEBUG
PrivilegesRequired=none
#endif
DisableStartupPrompt=true
OutputDir=.
OutputBaseFileName=cmissh-{#=APPVERSION}
ShowLanguageDialog=no
MinVersion=0,5.0.2195sp3
AppID={#=APPIDVAL}
UninstallFilesDir={app}\uninstall
Compression=lzma/ultra64
SolidCompression=yes
SetupLogging=yes
WizardImageFile=compiler:wizmodernimage-IS.bmp
WizardSmallImageFile=compiler:wizmodernsmallimage-IS.bmp
; The following breaks in older wine versions, so we
; check the wine version in the invoking script and
; define BADWINE, if we are crossbuilding and have a
; broken wine version.
#ifndef BADWINE
;SetupIconFile=setupdir\bin\nx.ico
#endif
;UninstallDisplayIcon={app}\bin\opennx.exe
;LicenseFile=lgpl.rtf

[Languages]
Name: "en"; MessagesFile: "compiler:Default.isl"
Name: "de"; MessagesFile: "compiler:Languages\German.isl"
Name: "es"; MessagesFile: "compiler:Languages\Spanish.isl"
Name: "fr"; MessagesFile: "compiler:Languages\French.isl"
Name: "ru"; MessagesFile: "compiler:Languages\Russian.isl"

[CustomMessages]
uninst_cmissh=Uninstall Cmis Shell
doc=Documentation
binshell=Start shell

de.uninst_cmissh=Deinstalliere Cmis Shell
de.doc=Dokumentation
de.binshell=Starte Kommandozeile

[Files]
Source: {#=BASEDIR}\target\*.jar; DestDir: {app};
Source: {#=BASEDIR}\scripts\*.bat; DestDir: {app};

[Icons]
;Name: "{group}\{cm:doc}"; Filename: "{app}\doc\index.html";
Name: "{group}\{cm:binshell}"; Filename: "{app}\cmissh.bat"; WorkingDir: "{app}"
Name: "{group}\{cm:uninst_cmissh}"; Filename: "{uninstallexe}";

[Code]
function IsJavaInstalled(): Boolean;
var b: Boolean;
var s: String;
begin
  b := RegQueryStringValue(HKLM, 'SOFTWARE\JavaSoft\Java Runtime Environment', 'CurrentVersion', s);
  Result := b and (CompareText(s, '1.6') >= 0);
end;

function RemoveOldWixInstall(): Boolean;
var b: Boolean;
var s: String;
var ret: Integer;
begin
    Result := True;
    b := RegQueryStringValue(HKLM, 'SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\{#=APPIDSTR}', 'UninstallString', s);
    if b and (Pos('msiexec', Lowercase(s)) > 0) then begin
        SuppressibleMsgBox('An old MSI-based installation was detected and must be removed.', mbError, MB_OK, IDOK);
        Result := Exec('msiexec.exe', '/qn /x {#=APPIDSTR}', '', SW_HIDE, ewWaitUntilTerminated, ret);
    end;
end;

function InitializeSetup(): Boolean;
begin
  Result := RemoveOldWixInstall();
  if not IsJavaInstalled() then begin
    SuppressibleMsgBox('Java is not installed. Please install JRE 1.6 or later.', mbError, MB_OK, IDOK);
    Result := False;
  end;
end;

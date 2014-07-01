%global ver 0.5.154
%global rel 1

Name:           gds2-cmissh
Version:        %{ver}
Release:        %{rel}
Summary:        Apache chemistry CMIS shell

Group:          Server Platform
License:        Copyright (C) 2013 GRAU DATA AG
Vendor:         GRAU DATA AG
URL:            http://www.graudata.com

BuildRoot:      %{_tmppath}/%{name}-%{version}-%{release}-root-%(%{__id_u} -n)
BuildArch:      noarch
BuildRequires:  make
Requires:       java >= 1.6

%description
This package provides a shell for accessing CMIS repositories.

%clean
rm -rf %{buildroot}

%install
if [ -z "%{?gds2root}" ] ; then
    "gds2root not defined" >&2
    exit 1
fi
rm -rf %{buildroot}
make -C %{gds2root} TARGET_SYS=rpm DESTDIR=%{buildroot} install

%files
%{_bindir}/*
%{_datadir}/java/*

%changelog
* Tue Jul  1 2014 Till Lorentzen <till.lorentzen@graudata.com> - 0.5.154-1
- gitrev: 252ac1ff0235f8e29e9780308f79fbc6d06f80b1
  Removed client compression
* Tue Jul  1 2014 Till Lorentzen <till.lorentzen@graudata.com> - 0.5.152-1
- gitrev: 2e87dc57d3a90a962ee71a71b1bee906728b1ad9
  Added client compression flag if using compression
* Tue Jul  1 2014 Till Lorentzen <till.lorentzen@graudata.com> - 0.5.151-1
- gitrev: 56d8d14c8e9921de5bee1b4625367a7ae957765c
  Added --compression description to usage message
* Tue Jul  1 2014 Build System <gds2dev@dataspace.cc> - 0.5.150-1
- Automated rebuild
* Mon Jun 30 2014 Till Lorentzen <till.lorentzen@graudata.com> - 0.5.149-1
- gitrev: 0e70a80e79bd18961da916fec04aec22201d0c83
  Merge branch 'master' of github.com:OpenDataSpace/chemistry-shell
- gitrev: bbf9ea9ab6d600ee1f21b7276878f59ce1a1e1f6
  Added rendition filter set and get operation to default context
* Mon Jun 30 2014 Achim Derigs <achim.derigs@graudata.com> - 0.5.148-1
- gitrev: d32beded55e3d9cae90f1ac9ea4caf245f8dd454
  Update maven plugins and dependencies
* Thu Jun 26 2014 Till Lorentzen <till.lorentzen@graudata.com> - 0.5.147-1
- gitrev: 60f7509e698511946aaca0667d5f7b326fbf3c49
  Merge branch 'master' of github.com:OpenDataSpace/chemistry-shell
- gitrev: 141b9cc6e3dc682663be3db8dc0ae024973df447
  Added the possibility to disable SSL certificate verification
* Mon Jun 23 2014 Build System <gds2dev@dataspace.cc> - 0.5.146-1
- Automated rebuild
* Wed Jun  4 2014 Build System <gds2dev@dataspace.cc> - 0.5.145-1
- Automated rebuild
* Wed Jun  4 2014 Build System <gds2dev@dataspace.cc> - 0.5.144-1
- Automated rebuild
* Wed May 28 2014 Build System <gds2dev@dataspace.cc> - 0.5.143-1
- Automated rebuild
* Fri May 23 2014 Build System <gds2dev@dataspace.cc> - 0.5.142-1
- Automated rebuild
* Wed Mar 26 2014 Build System <gds2dev@dataspace.cc> - 0.5.141-1
- Automated rebuild
* Tue Mar 25 2014 Build System <gds2dev@dataspace.cc> - 0.5.140-1
- Automated rebuild
* Tue Mar 25 2014 Till Lorentzen <till.lorentzen@graudata.com> - 0.5.139-1
- gitrev: c1d6a20ff538f866db36b152b0511c5152f2a9a4
  Added compression support flag to application
* Fri Jan 24 2014 Till Lorentzen <till.lorentzen@graudata.com> - 0.5.138-1
- gitrev: d6b1c678355702df41db7dd7c45eab9bb8284652
  Merge branch 'master' of github.com:OpenDataSpace/chemistry-shell
- gitrev: ece9e06dae909c42d6f7d69df824fa56762e92cb
  Added automatic mimetype detection to put command
* Fri Jan 24 2014 Till Lorentzen <till.lorentzen@graudata.com> - 0.5.137-1
- gitrev: 1f588ae21f76a0e4e003eea4ca3b46289d7bfe00
  Merge branch 'dev'
- gitrev: bf93bd8b56cdff97556c1e7be5bfa2afb6f5b522
  Added mimetype field to put command
* Fri Jan 24 2014 Build System <gds2dev@dataspace.cc> - 0.5.136-1
- Automated rebuild
* Fri Jan 17 2014 Fritz Elfert <fritz@fritz-elfert.de> - 0.5.135-1
- gitrev: 9eff78ee6306c7eb99229884aadb8ae8750e67cb
  Store versioned jar on Linux
* Fri Jan 17 2014 Fritz Elfert <fritz@fritz-elfert.de> - 0.5.134-1
- gitrev: 2e5661ed7cc2b62453d5b564a7dd2e1b00ce5314
  Fixed subst variable name
* Fri Jan 17 2014 Fritz Elfert <fritz@fritz-elfert.de> - 0.5.133-1
- gitrev: 225e228531559bd1be921c6b389250cbbef55ce3
  Ignore generated files
- gitrev: c14ae1b5caa330202ce2c5d140e1dd56a86f097f
  Moved scripts/cmissh to correct subdir
* Fri Jan 17 2014 Fritz Elfert <fritz@fritz-elfert.de> - 0.5.132-1
- gitrev: 54fc566c1c7ea4e776ab71061412e6ff929d9790
  Fixed a typo, jenkins hook test
* Fri Jan 17 2014 Fritz Elfert <fritz@fritz-elfert.de> - 0.5.131-1
- gitrev: 4af1768fc2e2e3b7577e08468607d9f63a158a23
  Specify includes for versions:use-latest-versions in pom.xml
- gitrev: a9df5c71ee8baaaaca74456d7a89520c7c1b46bc
  Ignore generated files
- gitrev: 44bc36145e92cbb5052bc4e3789e80988ab2e060
  Use maven-rsource-plugin to generate scripts
- gitrev: 0b48532391886c2d2206ad84f6806bf646ead002
  Remove old wixInstall
* Fri Jan 17 2014 Build System <gds2dev@dataspace.cc> - 0.5.130-1
- Automated rebuild
* Fri Jan 17 2014 Build System <gds2dev@dataspace.cc> - 0.5.128-1
- Automated rebuild
* Fri Jan 17 2014 Fritz Elfert <fritz@fritz-elfert.de> - 0.5.3-1
- gitrev: f2494ca6e709da20f9278a6d5d7c72823ed247d4
  Added iscc-based installer
- gitrev: 819e9640e2627865b3df009aff486876b6676dbe
  Initial RPM package, monolithic build
- gitrev: c5f71f468c436c8bfb7b024da079b971bc8de567
  Integrated packaging
- gitrev: f5927cd1f92030750685b67a91a317b33cc619b4
  Merge branch 'dev'
- gitrev: 98f02b0b2fe7f19148ef8f40e658ddef06fda83e
  Enabled Cookie Support for cmissh
- gitrev: bf46f904092e75c6956a0a3871ff424b2d2d46e7
  Added program param to enable time measurements by default
- gitrev: 887b5fe0ba6472a7bc99d820314260e7e72f0e77
  Merge branch 'master' into dev
- gitrev: ef97c146ca842a95c3861a398013603dd6519b5e
  Fixed merge conflict
- gitrev: efa6693382973579d5f8f0321f4ca074d2ed5260
  Added custom token and reposelection support
- gitrev: 535ce9fca66afedf1a3f8fd1f21173e929801a16
  Upgrading plugins
- gitrev: 16e6fae6895a7f1fe861a8a08d1701f2174a8e7f
  Added Exception handling for repos not supporting changes
- gitrev: d7162d2951abf245e5d850f5f6ccb0d416ea64d6
  Added benchmark options to mkfile command
- gitrev: ddf83d8a7f5afd8fc9028360109af1c2866995da
  Next try to create a clean IT move test
- gitrev: bd2582b7e047fca685afabfb640d65f47bc4b567
  IT: saver cleanup
- gitrev: 8455c5e2a37538db9fb5996c4c856235b4dd1b43
  Bugfix CompatibilityIT
- gitrev: 2f3d792fc3cf686454169467730d852b827350ac
  Added getContentChanges command "changes"
- gitrev: 7261fe2a0368c032e53b3507316f538e0712e2fb
  Added move command
- gitrev: ab92705e341c870b6a565ad5e1b131bb24014a36
  Added --force-walkthrough option to tree
- gitrev: 1d41a722e1c8c83687ac501d7e80c7731ed97f6c
  Added support for deleting documents
- gitrev: e88c5838003f7073926b445698feb7056ad05c92
  Added updatablility to propget/props command
- gitrev: 073ef7140cf01c556be9720b6f4b870d778ae5ef
  Bugfix for tree depth argument
- gitrev: 33e3d3aa22a749b814cc89f188431f8fa63fabb4
  Added getDescendants support to tree command
- gitrev: 309ceed35e892eafc0a789d76ae4b7c26392dc09
  Merge branch 'master' of github.com:OpenDataSpace/chemistry-shell
- gitrev: e6cb9c10d93c5b7c2dcf7af484a4b0b4f7c80de1
  Fixed tree command output
- gitrev: 31b6486d8603801293700a2d3057c0a1b18655d7
  Git attribute added for cmissh linux script
- gitrev: d0ba7cfd0b89e306e9f56febd0f701fc3c4fa185
  Merge branch 'dev'
- gitrev: f228a947d46de69efb1570be9e2f71d9718528ef
  Added timestamp support
- gitrev: 9b314597231d29bf83989aaaaf3faeab92e27c31
  Bugfix im WiX script
- gitrev: e9e913dac0c74103a0c58d958473653df0ead24f
  Merge branch 'master' into dev
- gitrev: 9f3a7fa732078568655b1f0c8e0d47c7cb15fd58
  Changed JRE Detection mode to support 32 and 64Bit
- gitrev: 52a1720d2f8c7120fc6b0dddfb079acdeec62a5d
  changed wix script
- gitrev: 19d2d9687d6e6c3a4138eeea4a66302dd87c58aa
  new variable introduced in WiX script
- gitrev: d5ab88c347ae2d400216b66c0f18ea87cc7b009d
  removed wix variables
- gitrev: 10a461bc0902b6a5243c82f92cf10ab92237a2f3
  next try to fix WiX script
- gitrev: 3bd526ce69aa749988d2bdc8174ffc80e35ff0c5
  next xml fix...
- gitrev: 76e82c0c62aad72393d8cf5f8a3d4abf3e9faac7
  Next try to fix the WiX script
- gitrev: 344b165a84760fcf9cf457f9ab4535852872d390
  Changed orderings and trying to fix bugs in WiX
- gitrev: 4033b6b25b512c2e77804e4ff9e64e4b174e2b48
  Added missing xml tag
- gitrev: 6e93efcdea5f3f35ba954e37ac3d4b36d96f5f76
  Added WiX description file for msi installer
- gitrev: 2027521e4ed7fc742e4d51a62a7224a17f376ac4
  Fixed wrong time format for ls -l cmd
- gitrev: c309745911d417f3a23609ae3e5076c0f4ae6067
  Started to implement ls -l option
- gitrev: 816fa865d832a62449514d88f771935684eaa179
  Added a optional target parameter to GET cmd
- gitrev: 09aa2a34b6578e64a36e0946bdbda0c58fab4860
  Added a dummy class for mv command
- gitrev: fe8bbe701686a7b056854b84328191896082a380
  Improved DateTime handling
- gitrev: 2d40673c29aef3ade278eb806c5c936b71fb65a9
  Generalized Integeration Test for different server installations
- gitrev: 6a5798cfb7b6d9d6e85ee0f56c9235647188ac84
  Added version and help printing arguments
- gitrev: a7d46d498ad5aa394df4a0915f4e358c90afad14
  Merge branch 'master' into dev
- gitrev: 4b71ba354594cf6c98ecefd8aaad0b58a66827c0
  Merge branch 'master' into dev
- gitrev: a586de9c7c9e9074198c9ba8f5dced37663a2de2
  Bugfix: Put command
- gitrev: c2b3c1092691805caf99cbbbabc9364a947bd7f3
  Bugfix: double requests for username and passwords solved
- gitrev: 8eaf8d331482c0bde90985797fd7d011d6bc3090
  Merge branch 'master' into dev
- gitrev: 8242f75439c05b2a7206ffb332cfe1105c732c93
  Merge branch 'master' into dev
- gitrev: c2b29ffe642d4901ba962e317154a33c8f538414
  improved usability
- gitrev: 840a29c7aea36265263bcff0e0d16b688dddbb1a
  Merge branch 'master' of github.com:OpenDataSpace/chemistry-shell
- gitrev: ddaa5fcf0da4b472ecdaf7f7c8dd98ad0455e608
  Fix quoting and use exec in shellscript
- gitrev: 90402bebc4802f6c4c3f21117324925b5c28dcfc
  Integration Test added
- gitrev: b6e069c30b94c64891c70a079e7754003aeaaf3c
  Added browser binding support
- gitrev: e21ea6077e60f512f3cf5c7eba38cda3e4b228eb
  Ported to JLine2 and Java 1.6
- gitrev: 334a8f05510723aacef0f35bb58470afab8e00db
  Changed the Jline Version from 0.9 to 2.11
- gitrev: 782bd0a0857c56d540ee23914de77dfc13c14d0b
  Bugfix: Printing correct time stamps
- gitrev: af8a83f60dee4c9e5be48566032ae38b58da81ea
  Added simple slf4j logger as dependency
- gitrev: e4bf557fedfa27550f7c2a90e26c92cb2b2beeb3
  Added shutdown message if program is killed
- gitrev: ffe704a954ed4230c8d1eb96312d7d84db78f6bd
  Removed sorting of cmis properties
- gitrev: d06696151eebc0e4cfff69487a1fb3030738fd32
  Bugfixes: NullPointerExceptions & Sort on property
- gitrev: 643b12919ed9800cc338018776b3370dde0204d9
  Changed the building order
- gitrev: ef269a067feebf3a65a28cefd7e2d881ddd4c656
  Readded maven dependency plugin
- gitrev: ab70cb7c0b1b00ee7fff725d1d14d2d623ee8fba
  Resolved a lot of bugs and removed maven plugin
- gitrev: 1e2a1b8df3dc9810f37a9cad777b19b0f1f6db8a
  Started to port the Shell to the new OpenCMIS lib
- gitrev: 894361af2d545e62e0edc8d034b2d53b438d1424
  Delivering continuously version 0.5.$BUILD_NUMBER
- gitrev: 24e8c5cbe9b3d2eaadec2e9af29edb845037df48
  Updating major version number
- gitrev: a4f179026f69be40e71a95efd5605edc6cdbbfeb
  Using -source 5 to enable generics
- gitrev: d328070b9356df650acf84a3e9575e3070484664
  Depending on Apache Chemistry OpenCMIS 0.9.0
- gitrev: 29c99cede0ef822549c2003082ffdfd5ead5565a
  Initial commit

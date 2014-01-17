%global ver 0.5.127
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

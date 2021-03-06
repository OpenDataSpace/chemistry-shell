TOPDIR = $(shell pwd)
SUBDIRS = gds2-cmissh
CHGFILES = $(addsuffix _$(VERSION)-*_amd64.changes,$(SUBDIRS))
RPMFILES = $(addprefix noarch/,$(addsuffix -$(VERSION)*.rpm,$(SUBDIRS)))

all:

dist: clean build upload

upload:
	@set -e; for c in $(CHGFILES) ; do \
		set -e; test -f $$c || (echo Missing $$c; exit 1) ; \
		(set -e; echo "Uploading $$c" && dput -c dput.cf $$c) ; \
	done
	@set -e; for r in $(RPMFILES) ; do \
		set -e; test -f $$r || (echo Missing $$r; exit 1) ; \
		(set -e; echo "Uploading $$r" && rput -c rput.cf $$r) ; \
		(set -e; echo "Uploading $$r" && rput -c r7put.cf $$r) ; \
		(set -e; echo "Uploading $$r" && rput -c sput.cf $$r) ; \
	done

build:
	@set -e; for d in $(SUBDIRS) ; do \
		$(MAKE) -C $$d buildnumber ; \
		cd $$d && dpkg-buildpackage -d -b -tc -us -uc && \
		rpmbuild -bb --define "gds2root `pwd`" --define "_rpmdir $(TOPDIR)" $$d.spec ; \
		cd $(TOPDIR) && iscc -DAPPVERSION=$(VERSION) -DBASEDIR=.. cmissh.iss ; \
	done

clean:
	@set -e; for d in $(SUBDIRS) ; do \
		$(MAKE) -C $$d clean ; \
	done
	rm -rf *.deb *.changes *.upload *.exe noarch

pre-integration-test: $(wildcard *.deb)
ifneq ($(HOST),)
	scp -o StrictHostKeyChecking=no -o CheckHostIP=no -p $^ $(HOST):
	ssh -o StrictHostKeyChecking=no -o CheckHostIP=no $(HOST) sudo dpkg -i $^
endif

post-integration-test: $(wildcard *.deb)
ifneq ($(HOST),)
	ssh -o StrictHostKeyChecking=no -o CheckHostIP=no $(HOST) sudo rm $^
endif

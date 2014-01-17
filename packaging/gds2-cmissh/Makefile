JARFILE=chemistry-shell-$(VERSION).jar
SHELLSCRIPT=cmissh

all:

buildnumber:
	perl git2cl.pl debian/changelog $(VERSION)
	perl git2spec.pl gds2-cmissh.spec $(VERSION)

clean:
	rm -f $(SHELLSCRIPT)

install: $(BASEDIR)/target/$(JARFILE) $(SHELLSCRIPT)
	install -d $(DESTDIR)/usr/share/java
	install -d $(DESTDIR)/usr/bin
	install -m 0644 $< $(DESTDIR)/usr/share/java/
	install -m 0755 $(SHELLSCRIPT) $(DESTDIR)/usr/bin

$(SHELLSCRIPT): $(BASEDIR)/scripts/$(SHELLSCRIPT)
	perl -p -e 's/^LIB_DIR=.*/LIB_DIR=\/usr\/share\/java/;' < $^ > $@

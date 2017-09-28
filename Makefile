NAME = Image_Processing
SOURCESDIR = src
HEADERDIR = include

JFLAGS = -g -cp src
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = $(shell find $(SOURCEDIR) -name '*.java')

default: classes

classes: $(CLASSES:.java=.class)

clean:
	find . -name *.class -exec rm -rf {} \;

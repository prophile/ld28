JAVAC=javac
JAVAC_FLAGS=-Werror -g

all: one.jar

one.jar: compile MANIFEST.MF
	find uk -name '*.class' | xargs jar cfm $@ MANIFEST.MF

compile:
	$(JAVAC) $(JAVAC_FLAGS) uk/co/alynn/Main.java

.PHONY: all compile


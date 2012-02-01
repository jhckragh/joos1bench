JOOS        := camljoos2
JOOSFLAGS   := -joos1

JASMIN      := jasmin

SOURCE_DIR  := src/textutils
OUTPUT_DIR  := build/classes

MKDIR       := mkdir -p
RM          := rm -f
SED         := sed

# Quotes around fields names must be removed to make Jasmin 2.4 happy
field-fixup := 's/^\.field \([a-z]\+\) "\([a-zA-Z0-9_$$]\+\)"/.field \1 \2/'

vpath %.java $(SOURCE_DIR)
vpath %.j $(SOURCE_DIR)

.PHONY: all
all: Echo.class Translate.class WordCount.class

.PHONY: clean
clean:
	$(RM) $(SOURCE_DIR)/*.j
	$(RM) -r $(OUTPUT_DIR)

.PHONY: prepare
prepare:
	$(if $(wildcard $(OUTPUT_DIR)),,$(MKDIR) $(OUTPUT_DIR))

%.class: %.j prepare
	$(JASMIN) -d $(OUTPUT_DIR) $(SOURCE_DIR)/$<

%.j: %.java
	$(JOOS) $(JOOSFLAGS) $<
	$(SED) -i $(field-fixup) $(subst .java,.j,$<)

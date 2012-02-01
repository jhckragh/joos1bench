JOOS        := camljoos2
JOOS_FLAGS  := -joos1

JASMIN      := jasmin

SOURCE_DIR  := src/joos1lexer
OUTPUT_DIR  := build/classes

MKDIR       := mkdir -p
RM          := rm -f
SED         := sed

# Quotes around fields names must be removed to make Jasmin 2.4 happy
field-fixup := 's/^\.field \([a-z]\+\) "\([a-zA-Z0-9_$$]\+\)"/.field \1 \2/'

create-output-dir := $(if $(wildcard $(OUTPUT_DIR)),,$(MKDIR) $(OUTPUT_DIR))

.PHONY: all
all:
	$(JOOS) $(JOOS_FLAGS) $(SOURCE_DIR)/*.java
	sed -i $(field-fixup) $(SOURCE_DIR)/*.j
	$(JASMIN) -d $(OUTPUT_DIR) $(SOURCE_DIR)/*.j
	$(RM) $(SOURCE_DIR)/*.j

.PHONY: clean
clean:
	$(RM) -r $(OUTPUT_DIR)

# For installing
XDG_DATA_HOME ?= $(HOME)/.local/share
MINDUSTRY := $(HOME)/.local/share/Mindustry

JAVAC := javac
JAVACFLAGS := -g -Xlint:all
# Auto-import files in the same package
override JAVACFLAGS += -sourcepath src
# Add .jar libraries
override JAVACFLAGS += -classpath "libs/*"
# fuck android all my homies hate android
override JAVACFLAGS += --release 17

JARFLAGS := -C build/classes .
override JARFLAGS += -C assets .

sources := $(shell find src -type f -name "*.java")
assets := $(shell find assets -type f)
classes := $(patsubst src/%.java, build/classes/%.class, $(sources))

# Mindustry version to build against
mindustry_version := 4dcf491abf
# Arc version to build against
arc_version := dfcb21ce56

all: build

libs := core-release arc-core
libs := $(libs:%=libs/%.jar)

define newlib
libs/$(1).jar:
	@printf "\033[33m> LIB\033[0m\t%s\n" $$@
	@mkdir -p $$(@D)
	@curl 'https://jitpack.io/com/github/$(2)/$(3)/$(4)/$(3)-$(4).jar.sha1' -o $$@.sha1 --no-progress-meter
	curl 'https://jitpack.io/com/github/$(2)/$(3)/$(4)/$(3)-$(4).jar' -o $$@ --no-progress-meter
	@printf "\t%s" "$$@" >> $$@.sha1
	@sha1sum -c $$@.sha1 || (rm $$@ && exit 1)
	@rm $$@.sha1
endef

$(eval $(call newlib,core-release,Anuken/MindustryJitpack,core,$(mindustry_version)))
$(eval $(call newlib,arc-core,Anuken/Arc,arc-core,$(arc_version)))

build: Routorio.jar

build/classes/%.class: src/%.java $(libs)
	@printf "\033[32m> JAVAC\033[0m\t%s\n" $@
	@mkdir -p `dirname $@`
	$(JAVAC) $(JAVACFLAGS) $< -d build/classes

Routorio.jar: $(classes) $(assets)
	@printf "\033[33m> JAR\033[0m\t%s\n" $@
	jar -cf $@ $(JARFLAGS) || rm $@

install: build
	cp Routorio.jar $(MINDUSTRY)/mods

clean:
	rm -rf build

reset:
	rm -rf build libs *.jar

help:
	@printf "\033[97;1mAvailable tasks:\033[0m\n"
	@printf "\t\033[32mbuild \033[90m(default)\033[0m\n"
	@printf "\t  Compile the mod into \033[97;1m%s\033[0m\n" Routorio.jar
	@printf "\t  Compatible with PC only.\n"
	@printf "\t\033[32minstall\033[0m\n"
	@printf "\t  Install the jar to \033[97;1m%s\033[0m\n" $(MINDUSTRY)
	@printf "\t\033[32mclean\033[0m\n"
	@printf "\t  Remove compiled classes.\n"
	@printf "\t\033[31mreset\033[0m\n"
	@printf "\t  Remove compiled classes and downloaded libraries.\n"

.PHONY: all libs build install clean reset help

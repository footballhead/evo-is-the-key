# Lame Makefile, just to make things a bit easier (see what I did there?)
# Compilation was initially managed by Eclipse, so this is just thrown together
# Since this is Java should Ant be used instead?

# Specifies the directory for LWJGL
LWJGL=lwjgl-2.8.4
# used for `make dist`; specifies the jar file in build/ which contains sources and resources
JAR_FILE=evo-is-the-key.jar
# The filename of the fat jar output
DIST_OUT=EvolutionIsTheKey.jar
# The class which defines the entry point `main`
MAIN_CLASS=net.blackforest.ld24.Game

# compile the sources
build:
	mkdir -p build
	cd src ; javac -cp ../${LWJGL}/jar/lwjgl.jar:../${LWJGL}/jar/lwjgl_util.jar:. -sourcepath .  -d ../build net/blackforest/ld24/Game.java

# run the sources
run:
	@echo I hope you compiled first!
	@# NOTE: If you load Solaris before Linux the thing refuses to run
	@# I'm not sure how this will work on Solaris then
	java -cp ${LWJGL}/jar/lwjgl.jar:${LWJGL}/jar/lwjgl_util.jar:build -Djava.library.path=${LWJGL}/native/linux:${LWJGL}/native/windows:${LWJGL}/native/mac:${LWJGL}/native/solaris ${MAIN_CLASS}
	
# create a fat jar; can be run with `java -jar EvolutionIsTheKey.jar`
dist:
	@echo I hope you compiled first!
	cd build ; jar cf ${JAR_FILE} *
	jar uf build/${JAR_FILE} data
	java -jar JarSplicePlus.jar -i build/${JAR_FILE} \
									${LWJGL}/jar/lwjgl.jar \
									${LWJGL}/jar/lwjgl_util.jar \
								-n ${LWJGL}/native/linux/liblwjgl.so \
									${LWJGL}/native/linux/liblwjgl64.so \
									${LWJGL}/native/windows/lwjgl.dll \
									${LWJGL}/native/windows/lwjgl64.dll \
									${LWJGL}/native/macosx/liblwjgl.jnilib \
								-m ${MAIN_CLASS} \
								-p -Xms256m -Xmx512m \
								-o ${DIST_OUT}

# remove all built sources
clean:
	rm -f -r build
	rm -f ${DIST_OUT}

.PHONY: build run clean dist

# Lame Makefile, just to make things a bit easier
# See what I did there?
# Compilation was initially managed by Eclipse, so this is just thrown together

build:
	mkdir -p build
	cd src ; javac -cp ../lwjgl-2.8.4/jar/lwjgl.jar:../lwjgl-2.8.4/jar/lwjgl_util.jar:. -sourcepath .  -d ../build net/blackforest/ld24/Game.java
	
run:
	@echo I hope you compiled first!
	java -cp lwjgl-2.8.4/jar/lwjgl.jar:lwjgl-2.8.4/jar/lwjgl_util.jar:build -Djava.library.path=lwjgl-2.8.4/native/windows:lwjgl-2.8.4/native/macosx:lwjgl-2.8.4/native/solaris:lwjgl-2.8.4/native/linux net.blackforest.ld24.Game
	
dist:
	@echo I hope you compiled first!
	cd build ; jar cf evo-is-the-key.jar *
	jar uf build/evo-is-the-key.jar data
	java -jar JarSplicePlus.jar -i build/evo-is-the-key.jar \
									lwjgl-2.8.4/jar/lwjgl.jar \
									lwjgl-2.8.4/jar/lwjgl_util.jar \
								-n lwjgl-2.8.4/native/linux/liblwjgl.so \
									lwjgl-2.8.4/native/linux/liblwjgl64.so \
									lwjgl-2.8.4/native/windows/lwjgl.dll \
									lwjgl-2.8.4/native/windows/lwjgl64.dll \
									lwjgl-2.8.4/native/macosx/liblwjgl.jnilib \
								-m net.blackforest.ld24.Game \
								-p -Xms256m -Xmx512m \
								-o EvolutionIsTheKey.jar
	
clean:
	rm -f -r build
	rm -f EvolutionIsTheKey.jar

.PHONY: build run clean dist

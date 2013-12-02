# Lame Makefile, just to make things a bit easier
# See what I did there?
# Compilation was initially managed by Eclipse, so this is just thrown together
# Also note: `make run` only works on Linux

build:
	mkdir -p build
	cd src ; javac -cp ../lwjgl-2.8.4/jar/lwjgl.jar:../lwjgl-2.8.4/jar/lwjgl_util.jar:. -sourcepath .  -d ../build net/blackforest/ld24/Game.java
	
run:
	java -cp lwjgl-2.8.4/jar/lwjgl.jar:lwjgl-2.8.4/jar/lwjgl_util.jar:build -Djava.library.path=lwjgl-2.8.4/native/linux net.blackforest.ld24.Game
	
clean:
	rm -f -r build

.PHONY: build run clean

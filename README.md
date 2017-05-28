# Evolution is the Key

Source code for my _Ludum Dare_ 24 Jam entry _Evolution is the Key_.
You can check it the entry
[here](http://www.ludumdare.com/compo/ludum-dare-24/?action=preview&uid=16438)
or download it
[here](https://44d-again-again.itch.io/evolution-is-the-key)

## Requirements

  * Java JDK 1.8
  * Apache ant

## Compilation

```
ant <target>
```

There are several targets:

  * compile: run javac on the source files; output is in `build/`
  * jar (default): bundle all the built files into `ld24.jar`
  * fatjar-windows: create `EvolutionIsTheKey.jar` with Windows natives, ready to run
  * fatjar-macos: create `EvolutionIsTheKey.jar` with macOS natives, ready to run
  * fatjar-linux: create `EvolutionIsTheKey.jar` with Linux natives, ready to run
  * clean: remove all artifacts

## Running

Build one of the fatjar-* targets, then run `java -jar EvolutionIsTheKey.jar`.

## Distribution

Package the appropriate fatjar-* output with the `data/` directory.

## Some Notes

This source is **v1**. The released game is **v4**. Therefore, there are several
fixes incorporated into releases that were not merged back into the source. I
think some of them are sound related, but I honestly can't remember.

Also, the code is a mess. When you only have 72 hours you don't generally worry
about making your code look pretty.

Also also, I had **plenty** of trouble with sound, especially since it has
behaved differently on **every single system I've tested on**. Have fun with
that.

## Licenses

Everything under `src` and `data` was made by me and is under the MIT license.

This game uses the _Leightweight Java Game Library_, see
`lwjgl-2.9.3/doc/LICENSE`; I'm only including it for convenience (which I
believe the license allows). You can find LWJGL builds
[here](http://legacy.lwjgl.org):

_JarSplicePlus_, `JarSplicePlus.jar` has it's own license, whose terms are
written in `JARSPLICEPLUS-LICENSE`. You can find _JarSplicePlus_
[here](https://github.com/lquesada/JarSplicePlus):

## Programs Used (if you care)

  * Java: The whole thing is coded in Java, because I was a fan of the language
  * Eclipse: _The_ best Java IDE
  * Lightweight Java Game Library: _The_ best Java OpenGL + OpenAL solution
  * Audacity: Mostly for generating white noise
  * AS3SFXR: Used for all other sounds; used over SFXR because Flash is cross-platform and I was developing on Linux
  * Gnu Image Manipulation Program: Used for all the GFX
  * JarSplicePlus: Used now instead of JarSplice because it offers command-line support

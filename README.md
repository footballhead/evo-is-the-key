# Evolution is the Key

Source code for my _Ludum Dare_ 24 Jam entry _Evolution is the Key_.
You can check it the entry
[here](http://www.ludumdare.com/compo/ludum-dare-24/?action=preview&uid=16438)
or download it
[here](https://44d-again-again.itch.io/evolution-is-the-key).

## Requirements

  * Java JDK 1.6+ (Oracle or OpenJDK)
  * Apache ant

## Compilation

```
ant <target>
```

There are several targets:

  * **compile**: run javac on the source files; output is in `build/`
  * **jar** (default): bundle all the built files into `ld24.jar`
  * **dist**: create the `dist/` directory with platform-independent files for distribution
  * **fatjar-windows**: create `dist/EvolutionIsTheKey.jar` with Windows natives, ready to run
  * **fatjar-mac**: create `dist/EvolutionIsTheKey.jar` with macOS natives, ready to run
  * **fatjar-linux**: create `dist/EvolutionIsTheKey.jar` with Linux natives, ready to run
  * **dist-zip**: zip the contents of `dist/` into `EvolutionIsTheKey.zip`
  * **clean**: remove all artifacts

## Running

Build one of the **fatjar-*** targets, then navigate into `dist/` and run
`java -jar EvolutionIsTheKey.jar`. Depending on your platform, you may be able
to double-click `EvolutionIsTheKey.jar` to launch.

## Distribution

Build a **fatjar-*** target, then build **dist-zip** to create
`EvolutionIsTheKey.zip`.

## Some Notes

  * ~~This source is **v1**. The released game is **v4**.~~ Based on my
    adventures decompiling my submission, it looks like this is the **v4**
    source after all.
  * The code is a mess because I only had 72 hours.
  * Sound works inconsistently across platforms (thanks Java). Support looks
    better now than it did five years ago, though, so you may be lucky.
      * That said, there's no easy way to compile without sounds.

## Licenses

Everything under `src/` and `data/` was made by me and is under the MIT license
(see `LICENSE`).

This game uses the _Leightweight Java Game Library_, see
`lwjgl-2.9.3/doc/LICENSE`. You can find _LWJGL_ builds
[here](http://legacy.lwjgl.org).

_JarSplicePlus_ is used for fat jar packaging and distribution. It has its own
license included as `JARSPLICEPLUS-LICENSE`. You can find _JarSplicePlus_
[here](https://github.com/lquesada/JarSplicePlus).

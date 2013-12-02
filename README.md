Evolution is the Key
====================

Source code for my _Ludum Dare_ #24 Jam entry titled _Evolution is the Key_.
You can check it out here:

```
http://www.ludumdare.com/compo/ludum-dare-24/?action=preview&uid=16438
```

Why not check out _Ludum Dare_ while you're at it?

```
www.ludumdare.com
```

Requirements
------------

To compile you must have a later version of the _Java Development Kit_. You can
download that from Oracle:

```
http://www.oracle.com/technetwork/java/javase/downloads/index.html
```

If you're using Linux I'd recommend _OpenJDK_, which you can find through your
distribution's package manager. I'm pretty sure it's what I used to compile the
original release.

Compilation
-----------

```
make
```

Running
-------

```
make run
```

Note: only works on Linux; you'll have to change the native path for other
systems.

Distribution
------------

To distribute the release for the competition I used _JarSplice_:

```
www.ninjacave.com/jarsplice
```

It lets you put all your data, natives, libraries, and classes into one
executably jar. It's very handy.

I've included it as part of the project for convenience sake.

Some Notes
----------

This source is **v1**. The released game is **v4**. Therefore, there are several
fixes incorporated into releases that were not merged back into the source. I
think some of them are sound related, but I honestly can't remember.

Also, the code is a mess. When you only have 72 hours you don't generally worry
about making your code look pretty.

Just thought I should give you a heads up.

License
-------

Everything under `src` and `data` I'ved licensed under the _Do What the Fuck You
Want to Public License_. You can find it in the `LICENSE` file, or from the
WTFPL website:

```
www.wtfpl.net
```

The _Leightweight Java Game Library_, found under `lwjgl-2.8.4`, is licensed
under it's own lisence, found in `lwjgl-2.8.4/doc/LICENSE`; I'm only including
it for convenience (which I believe the license allows). You can find LWJGL
builds here:

```
www.lwjgl.org
```

I have no idea what license JarSplice is under (`jarsplice-0.40.jar`).

Programs Used
-------------

  * Java -- `www.java.com` -- The whole thing is coded in Java, because I am a fan of the language
  * Eclipse -- `www.eclipse.org` -- _The_ best Java IDE
  * Lightweight Java Game Library -- `www.lwjgl.org` -- _The_ best Java OpenGL + OpenAL solution
  * Audacity -- `audacity.sourceforge.net` -- Mostly for generating white noise
  * AS3SFXR -- `www.superflashbros.net/as3sfxr/` -- Used for all other sounds; used over SFXR because Flash is cross-platform and I was developing on Linux
  * GIMP -- `www.gimp.org` -- Used for all the GFX
  * JarSplice -- `www.ninjacave.com/jarsplice` -- Used for distribution of release
  
If you're on Linux some of these programs can be found through your
distribution's package manager.

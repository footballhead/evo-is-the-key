#!/bin/sh

# Remove old artifacts
rm -rf dist/
rm -rf dist-deb/
rm -f evolution-is-the-key.deb

# Make staging directory structure
mkdir -p dist-deb/usr/bin
mkdir -p dist-deb/usr/share/evolution-is-the-key
mkdir -p dist-deb/usr/share/applications

# Compile fatjar
ant fatjar-linux

# Copy things into place
cp -r DEBIAN dist-deb/

cp -r data dist-deb/usr/share/evolution-is-the-key
cp -r dist/licenses dist-deb/usr/share/evolution-is-the-key
cp dist/EvolutionIsTheKey.jar dist-deb/usr/share/evolution-is-the-key/game.jar

cp evolution-is-the-key dist-deb/usr/bin

cp icon.png dist-deb/usr/share/evolution-is-the-key
cp evolution-is-the-key.desktop dist-deb/usr/share/applications

# Build the deb
dpkg-deb -b dist-deb evolution-is-the-key.deb

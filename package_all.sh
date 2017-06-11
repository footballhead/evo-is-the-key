#!/bin/bash

set -e

ant fatjar-windows
ant dist-zip
mv EvolutionIsTheKey.zip EvolutionIsTheKey-Win.zip

ant fatjar-mac
ant dist-zip
mv EvolutionIsTheKey.zip EvolutionIsTheKey-Mac.zip

ant fatjar-linux
ant dist-zip
mv EvolutionIsTheKey.zip EvolutionIsTheKey-Lnx.zip

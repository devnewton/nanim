#!/bin/sh
nanimenc -a walk -d 100 -f original_png_files/hero00.png -f original_png_files/hero01.png -f original_png_files/hero02.png -f original_png_files/hero03.png -f original_png_files/hero04.png -f original_png_files/hero05.png -f original_png_files/hero06.png -f original_png_files/hero07.png -o newton.nanim
nanimdec -i newton.nanim
nanimopt -debug -i newton.nanim -o optimized_newton.nanim
nanimdec -i optimized_newton.nanim
nanimview -i optimized_newton.nanim


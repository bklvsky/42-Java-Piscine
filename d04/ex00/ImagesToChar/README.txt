=> Remove old build directory:

rm -rf ./target

=> Make target directory with:

mkdir target

=> Compile .class files into 'target' directory with:

javac -d ./target ./src/java/edu/school21/printer/*/*

=> Run with:

java -classpath ./target edu.school21.printer.app.Program 0 . ../../it.bmp

=> The commands follows the pattern:

=> java -classpath ./target edu.school21.printer.app.Program [BLACK_SYMBOL] [WHITE_SYMBOL] [PATH_TO_IMAGE]

=> You can modify symbols as you wish and provide new images.

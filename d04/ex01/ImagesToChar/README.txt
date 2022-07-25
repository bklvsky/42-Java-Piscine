=> Remove old build directory:

rm -rf ./target

=> Make target directory with:

mkdir target
cp -r ./src/resources ./target

=> Compile .class files into 'target' directory with:

javac -d ./target ./src/java/edu/school21/printer/*/*.java

=> Make an archive with:

jar -cfm ./target/images-to-chars-printer.jar ./src/manifest.txt -C ./target .
chmod u+x ./target/images-to-chars-printer.jar

=> Run with:

java -jar ./target/images-to-chars-printer.jar . 0

=> The commands follows the pattern:

=> java -jar ./target/images-to-chars-printer.jar [BLACK_SYMBOL] [WHITE_SYMBOL]

=> You can modify symbols as you wish.

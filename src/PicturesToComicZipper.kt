import java.io.*

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


fun main(args: Array<String>) {


    File("/home/sanmi/Documents/test").listFiles().forEach {


        renameFiles(it)

    }

}


fun renameFiles(file: File) {
    when {
        !file.isFile && file.isDirectory && !file.isHidden -> {

            var filesToZip: MutableList<File> = mutableListOf<File>()
            file.listFiles().forEach {
                try {
                    when {
                        it.isFile && !it.isDirectory -> {

                            var filename: String = it.name


                            if (filename.startsWith("RC", false)) {
                                filesToZip.add(it)

                            }


                        }
                    }


                } catch (e: StringIndexOutOfBoundsException) {
                    println("Files not renamed. Try again")
                }

                zipFiles(file.nameWithoutExtension, filesToZip)


            }


        }
    }


}

fun zipFiles(zipName: String, list: MutableList<File>) {
    val f = FileOutputStream("/home/sanmi/Documents/test/$zipName.cbr ")

    val out = ZipOutputStream(BufferedOutputStream(f))


    list.forEach {


        try {
            val input = File(it.path);
            val fis = FileInputStream(input);
            val ze = ZipEntry(input.name);
            System.out.println("Zipping the file: " + input.getName());
            out.putNextEntry(ze);

            System.out.println("Done... Zipped the files...");

        } catch (e: IOException) {

        }

    }

    out.closeEntry()

    out.close()


}


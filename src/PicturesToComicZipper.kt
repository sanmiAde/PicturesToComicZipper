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


//        try {
//            val fis = FileInputStream(it);
//            val ze = ZipEntry(it.name);
//            System.out.println("Zipping the file: " + it.getName());
//            out.putNextEntry(ze);
//
//            System.out.println("Done... Zipped the files...");
//
//        } catch (e: IOException) {
//
//        }

        val fileInputStream : FileInputStream = FileInputStream(it)
        val zipEntry : ZipEntry = ZipEntry(it.name)
        out.putNextEntry(zipEntry)

        val bytes = ByteArray(1024)
        var lenght = fileInputStream.read(bytes)
        System.out.println("Zipping the file: " + it.getName());
        while (lenght >= 0){
            out.write(bytes, 0, lenght)
            lenght = fileInputStream.read(bytes)
        }
        System.out.println("Done... Zipped the files...");


    }


    out.closeEntry()


    out.close()


}


import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
import java.util.zip.ZipEntry


import java.util.zip.ZipOutputStream


fun main(args: Array<String>) {

    when {
        args.isEmpty() -> println("Directory path can't be blank.")
        else -> {
            val location = args[0].trim()

            when {
                location.isBlank() -> {
                    println("Please enter an existing directory path.")

                }
                else -> {
                    val folderExists = Files.exists(Paths.get(location))

                    when {
                        folderExists -> {
                            File(location).listFiles().forEach {


                                getFilesToZip(it, location)

                            }
                        }


                    }
                }

            }
            println("Files have been successfully renamed")
        }
    }

}


/***
 * @Folder that contains pictures to be zipped.
 * @location Location of the folder.
 */
fun getFilesToZip(folder: File, location: String) {
    when {
        !folder.isFile && folder.isDirectory && !folder.isHidden -> {

            var filesToZip: MutableList<File> = mutableListOf<File>()
            folder.listFiles().forEach {
                try {
                    when {
                        it.isFile && !it.isDirectory -> {

                            val filename: String = it.name

                            if (filename.startsWith("RC", false)) {
                                filesToZip.add(it)
                            }

                        }
                    }

                } catch (e: StringIndexOutOfBoundsException) {
                    println("Files not renamed. Try again")
                }

            }

            zipFiles(folder.nameWithoutExtension, filesToZip, location)
            filesToZip = mutableListOf<File>()

        }
    }


}

/***
 * @zipname: The name of the zip to be created
 *
 * @list: The list of files to be zipped.
 */
fun zipFiles(zipName: String, list: MutableList<File>, location: String) {

    val strippedZipName = zipName.substringBefore("-")
    println(strippedZipName)
    val f = FileOutputStream("$location/$strippedZipName.cbz")
    val out = ZipOutputStream(BufferedOutputStream(f))

    list.forEach {

        val fileInputStream: FileInputStream = FileInputStream(it)
        val zipEntry: ZipEntry = ZipEntry(it.name)
        out.putNextEntry(zipEntry)

        val bytes = ByteArray(1024)
        var lenght = fileInputStream.read(bytes)

        while (lenght >= 0) {
            out.write(bytes, 0, lenght)
            lenght = fileInputStream.read(bytes)
        }

    }
    println("Done... Zipped the files... $strippedZipName ")

    out.closeEntry()


    out.close()

}


//Todo clean up folder leaving the zipped comic.

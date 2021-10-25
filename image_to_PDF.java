public class ImageToPdfConverter {

	public static void main(String arg[]) throws Exception {
		convertImageToPdf("C:/files/input", "C:/files/output", "output.pdf");
	}

	public static void convertImageToPdf(final String fileInputPath, final String fileOuputPath,
			final String outputFileName) throws DocumentException, IOException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(new File(fileOuputPath, outputFileName)));
		document.open();

		List<File> files = Files.list(Paths.get(fileInputPath)).map(Path::toFile).collect(Collectors.toList());

		files.forEach(f -> {
			document.newPage();

			Image image = null;
			try {
				image = Image.getInstance(new File(fileInputPath, f.getName()).getAbsolutePath());
			} catch (Exception e) {
				e.printStackTrace();
			}

			image.setAlignment(Element.ALIGN_CENTER);

			float imageWidth = image.getWidth();

			if (imageWidth > document.getPageSize().getWidth()) {
				int indentation = 0;
				float scaler = ((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin()
						- indentation) / image.getWidth()) * 100;
				image.scalePercent(scaler);
			}

			try {
				document.add(image);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		document.close();
	}

}

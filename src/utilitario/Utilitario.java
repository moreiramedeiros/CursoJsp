package utilitario;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;

public class Utilitario {
	

	/* Converte valor tipo Part para base64*/
	public String convertArquivoPartToBase64(Part file) throws IOException {
			/* Inicio Imagem original */		
			// Converte a entrada de fluxo de dados para bytes 
    	byte[] bytesImagem = convertStreamParaByte(file.getInputStream());
					
		// Converte o arquivo byte para base64
		String fotobase64 = new Base64().encodeBase64String(bytesImagem);
		return fotobase64;
	}
	
	
	/* Converte a entrada de fluxo de dados da imagem em um array de bytes */
	private byte[] convertStreamParaByte(InputStream imagem) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imagem.read();

		while (reads != -1) {
			baos.write(reads);
			reads = imagem.read();
		}

		return baos.toByteArray();
	}

	// Altera o tamanho da imagem base64 e concatena
	public String alteraTamanhoDaImagemBase64EConcatena(String fotobase64, int width, int height) throws IOException {
		/*Inicio da miniatura*/

		
		//Transforma em bufferImagem
		byte[] imageByteDecode = new Base64().decodeBase64(fotobase64);
		BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));
		
		//Pega o tipo de imagem
		int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

		//Cria imagem em miniatura redefinindo largura, altura e tipo
		BufferedImage resizeImage = new BufferedImage(width,height,type);
		Graphics2D g = resizeImage.createGraphics();
		g.drawImage(bufferedImage, 0, 0, width, height, null);
		g.dispose();
		
		//Escrever a imagem novamente
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(resizeImage, "png", baos);
		String miniaturaBase64 = "data:image/png;base64,"+ DatatypeConverter.printBase64Binary(baos.toByteArray());
		return miniaturaBase64;
	}
	
	
	// concatena ContentTy com Base64
	public String concatenaContentTyComFotoBase64(String contentType, String base64) {
		return  "data:" + contentType + ";base64," + base64;
	 
	}
	
	// concatena ContentTy com Base64
	public String concatenaContentTyComFotoBase64(Part file) throws IOException {
		String base64 = convertArquivoPartToBase64(file);
		return  "data:" + file.getContentType() + ";base64," + base64;
	 
	}
	
	
	public void download(HttpServletResponse response, String contentType, String base64) throws IOException {

		
		byte[] filebytes = new Base64().decodeBase64(base64);
		
		response.setHeader("Content-Disposition",
				"attachment;filename=arquivo." + contentType.split("\\/")[1]);

		// Coloca bytes em um objeto para processar
		InputStream is = new ByteArrayInputStream(filebytes);

		// Inicio da resposta ao navegador
		int read = 0;
		byte[] bytes = new byte[1024];
		OutputStream os = response.getOutputStream();

		while ((read = is.read(bytes)) != -1) {
			os.write(bytes, 0, read);
		}

		os.flush();
		os.close();
		
	}

}

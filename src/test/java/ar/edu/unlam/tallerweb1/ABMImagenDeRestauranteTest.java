package ar.edu.unlam.tallerweb1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.unlam.tallerweb1.servicios.ComidaService;
import ar.edu.unlam.tallerweb1.servicios.RestauranteService;

public class ABMImagenDeRestauranteTest extends SpringTest {
	
	@Inject
	private RestauranteService servicioRestaurante;
	
	@Inject
	private ServletContext servletContext;
	
    @Test
    @Transactional @Rollback
    public void pruebaConexion(){
        assertThat(session().isConnected()).isTrue();
    }
    
    @Test
	@Transactional @Rollback
	public void testQueValidaCorrectamenteExtensionDeImagenJPG() {
	    MockMultipartFile imagenJpg = new MockMultipartFile(
	    		"test", 
	    		"test.jpg",
	    		MediaType.IMAGE_JPEG_VALUE, 
	    		"Hola Mundo".getBytes()
	    	);
	    
	    Boolean verificacion = servicioRestaurante.verificarExtensionDeImagen(imagenJpg);
	    
	    assertTrue(verificacion);
	}
    
    @Test
	@Transactional @Rollback
	public void testQueValidaCorrectamenteExtensionDeImagenPNG() {
	    MockMultipartFile imagenJpg = new MockMultipartFile(
	    		"test", 
	    		"test.png",
	    		MediaType.IMAGE_PNG_VALUE, 
	    		"Hola Mundo".getBytes()
	    	);
	    
	    Boolean verificacion = servicioRestaurante.verificarExtensionDeImagen(imagenJpg);
	    
	    assertTrue(verificacion);
	}
    
    @Test
	@Transactional @Rollback
	public void testQueValidaIncorrectamenteExtensionDeImagen() {
	    MockMultipartFile imagenJpg = new MockMultipartFile(
	    		"test", 
	    		"test.txt",
	    		MediaType.TEXT_PLAIN_VALUE, 
	    		"Hola Mundo".getBytes()
	    	);
	    
	    Boolean verificacion = servicioRestaurante.verificarExtensionDeImagen(imagenJpg);
	    
	    assertFalse(verificacion);
	}
    
//    @Test
//	@Transactional @Rollback
//	public void testQueSubeUnaImagenJpg() throws Exception {
//
//    	MultipartFile imagen = this.getImagenJpgMultipartParaTest();
//
//	    servicioRestaurante.subirImagenRestaurante(imagen);
//	    
//	    String pathDeImagenSubida = servletContext.getRealPath("/") +
//				   "\\img\\restaurantes\\" +
//				   imagen.getOriginalFilename();
//	    
//	    File imagenSubida = new File(pathDeImagenSubida);
//	    
//	    assertTrue(imagenSubida.exists());
//	    
//	    imagenSubida.delete();
//	}
    
//    @Test
//	@Transactional @Rollback
//	public void testQueSubeUnaImagenPng() throws Exception {
//
//    	MultipartFile imagen = this.getImagenPngMultipartParaTest();
//
//	    servicioRestaurante.subirImagenRestaurante(imagen);
//	    
//	    String pathDeImagenSubida = servletContext.getRealPath("/") +
//				   "\\img\\restaurantes\\" +
//				   imagen.getOriginalFilename();
//	    
//	    File imagenSubida = new File(pathDeImagenSubida);
//	    
//	    assertTrue(imagenSubida.exists());
//	    
//	    imagenSubida.delete();
//	}
    
    
    
    
	public MultipartFile getImagenJpgMultipartParaTest() throws Exception {
		
		String imagenPath = servletContext.getRealPath("/") + "\\img\\test\\test1.jpg";
		File imagenJpg = new File(imagenPath);
		
		FileInputStream input = new FileInputStream(imagenJpg);
		
		MultipartFile imagenMultipart = new MockMultipartFile(
				"file",
				imagenJpg.getName(),
				"text/plain",
				IOUtils.toByteArray(input)
			);
		
		return imagenMultipart;
	}
	
	public MultipartFile getImagenPngMultipartParaTest() throws Exception {
			
		String imagenPath = servletContext.getRealPath("/") + "\\img\\test\\test2.png";
		File imagenJpg = new File(imagenPath);
		
		FileInputStream input = new FileInputStream(imagenJpg);
		
		MultipartFile imagenMultipart = new MockMultipartFile(
				"file",
				imagenJpg.getName(),
				"text/plain",
				IOUtils.toByteArray(input)
			);
		
		return imagenMultipart;
	}
	
}

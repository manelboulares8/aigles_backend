package com.manel.aigles.controller;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.manel.aigles.model.FormData;
import com.manel.aigles.service.FormDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.manel.aigles.model.FormData;
import com.manel.aigles.service.EncryptionService;
import com.manel.aigles.service.FormDataService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // Autoriser Angular uniquement
public class QRController {
    
    @Autowired
    private FormDataService formDataService; // Injection de la dépendance FormDataService
    
    @Autowired
    private EncryptionService encryptionService;  // Une seule instance du service EncryptionService
    
    // Valeurs fixes pour KEY et HSH
  /*  private static final String FIXED_KEY = "9Z50KslyeBXtmn1nFgqXyRjVSLM/gVGyNJ6Oq3Lo6fdIyypx8KHDiiYnua7Mv7gTRBdfy1+JVMNxT03vDMP71u5UQKuUmvoV5Q3YoPHeQpuuiqXQW0BoIMh2Az4bAcCJYbnFiXFbeT9cP9yJWmOFUm92hkYXAmvcaRh59XSS7A6WsJUrgixx2ABIZHcmQinZj2ATwvTuC1lEM8itfvZIhAH1I9DHZQZQeI4f0u6kM+oWU1pxFBNNDzZny2PiC5+kjSTIhZvXaP82XEkE69P9r+moTHpeh1GXPybsstsAgQKDB1C9deqCpRhPAbDgpC93RBfdDDcidmUJTwPLI8mIy+MGReJ6S1xQl2rIZpN056VQ+8TQBMHosrworRZJFUAi/qM+DGtjIZCKCA5aJJgSqFywOc4ucq9G/TP1HnMXwW3dWBj86mIpCqbzyTtg/wu2HL+jJXoM7ZZEsQYWBVSyes/jIb0CqOZ8E3vo+OhBnZa9go6lPaLt97PBn3MisiJ9WKnANYu1w8=";
    private static final String FIXED_HSH = "0N0ocytcHB0i5s4Fn9EooiJWw/dr+PBDVgDNW9i5kOlOShsO6pcMsuXjT/6E7+xJjhYvha98R4Vv8H+7JZhUtM3Np37f833SjqYVi9Noszz8FhbOrPLQcEDb477Pa2MMOJwmO3J8n+fn9NFPAlqYd0pEgkL4/dJkfYEjvHNGvS3mRea8LGva2qq/1KmKPUbCQdQL5Wcn0NGBBCRvCjYGN35DVT5BOE6fkv9pLR5zS0Jm+ZFYmFr/qp4QIuK7KPDlZQLVKMAwYSe8poi1lzEIWgIhqJIl+EMSVi2xGEW/kpdm4JxR4NXu18XJAkLMX5NY70DODMHrYqJyjBC7oCM5x5YoYlyBZn+EC9jFv8o3gwgu7UCfpWdna+absVjOEM1ufHseo7vxXIGgyJRvnGKX3j5cO4FJylqP5s1Lr/Wg3L3ZUTSrwCqf2AzhShdydFnxAxFEP6yTY4=";

    @PostMapping("/generate")
    public String generateQRCode(@RequestBody FormData formData) throws WriterException, IOException {
        // Formatage des données dans le format demandé
        String formattedText = String.format(
            "<p>Matricule: %s</p>\n" +
            "<p>Nom et prénom : %s %s</p>\n" +
            "<p>Acte : %s</p>\n" +
            "<p>Classe : %s</p>\n" +
            "<p>Echelon : %s</p>\n" +
            "<p>Grade : %s</p>",
            formData.getMatricule(),
            formData.getNomPrenom(),
            formData.getActe(),
            formData.getClasse(),
            formData.getEchelon(),
            formData.getGrade()
        );

        // Cryptage uniquement du contenu de <TXT>
        String encryptedText = encryptionService.encrypt(formattedText);

        // Formatage des données en XML avec <TXT> crypté et <KEY> et <HSH> en clair
        String xmlData = String.format(
            "<?xml version=\"1.0\"?><TXT>%s</TXT><KEY>%s</KEY><HSH>%s</HSH>",
            encryptedText, FIXED_KEY, FIXED_HSH
        );

        // Génération du QR code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(xmlData, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(pngData);
    }
*/
    
    @PostMapping("/add-and-generate-qr-pdf")
    public ResponseEntity<ByteArrayResource> addAndGenerateQRPDF(@RequestBody FormData formData)throws WriterException, IOException, DocumentException{
    	System.out.println("dataform"+formData);
    	 FormData savedFormData = formDataService.addFormData(formData);
    	 // 2. Générer le code QR
         String xmlData = "<?xml\r\n"
         		+ " version=\"1.0\"?><TXT>hBzciFwyJbmwzjwtgJA0p4FPT/qtFIABJdf5Qaz06LYm1c6OECGr6mDspgXZzzW/W4dncyk7RORkzp38qYMZNSQ9b4AK+OldVVZUN\r\n"
         		+ " onzt6u0uF8zWJc+5G13JjFl6WnFLJKxk9wevFDqDkfS4nibUs1lty0VpYNzYDafllzqGqimidFzttzgq0d6gwyjq+m7nrvTe4L7zz9FC0pjKpuaiPGVUfhABSJmbYfu\r\n"
         		+ " FMWAW3eVTQ5PyvCQFROn1N+HTSUVpYk06coYSwlS3NvJtorfT9+0J49iAXElhJxxhK0bAS6cIm4H0+D6OlcGZed38x1sdflClSkvL/V74Z6ZBhgLLHsalLkNb\r\n"
         		+ " RfE+iCLedJI</TXT><KEY>9Z50KslyeBXtmn1nFgqXyRjVSLM/gVGyNJ6Oq3Lo6fdIyypx8KHDiiYnua7Mv7gTRBdfy1+JVMNxT03vDMP71u5UQKuUmvoV5Q\r\n"
         		+ " 3YoPHeQpuuiqXQW0BoIMh2Az4bAcCJYbnFiXFbeT9cP9yJWmOFUm92hkYXAmvcaRh59XSS7A6WsJUrgixx2ABIZHcmQinZj2ATwvTuC1lEM8itfvZIhAH1I\r\n"
         		+ " 9DHZQZQeI4f0u6kM+oWU1pxFBNNDzZny2PiC5+kjSTIhZvXaP82XEkE69P9r+moTHpeh1GXPybsstsAgQKDB1C9deqCpRhPAbDgpC93RBfdDDcidmUJTJ\r\n"
         		+ " wPLI8mIy+MGReJ6S1xQl2rIZpN056VQ+8TQBMHosrworRZJFUAi/qM+DGtjIZCKCA5aJJgSqFywOc4ucq9G/TP1HnMXwW3dWBj86mIpCqbzyTtg/wu2HL+jJ\r\n"
         		+ " XoM7ZZEsQYWBVSyes/jIb0CqOZ8E3vo+OhBnZa9go6lPaLt97PBn3MisiJ9WKnANYu1w8=</KEY><HSH>0N0ocytcHB0i5s4Fn9EooiJWw/dr+PBDVgDNW9\r\n"
         		+ " i5kOlOShsO6pcMsuXjT/6E7+xJjhYvha98R4Vv8H+7JZhUtM3Np37f833SjqYVi9Noszz8FhbOrPLQcEDb477Pa2MMOJwmO3J8n+fn9NFPAlqYd0pEgkL4/dJk\r\n"
         		+ " fYEjvHNGvS3mRea8LGva2qq/1KmKPUbCQdQL5Wcn0NGBBCRvCjYGN35DVT5BOE6fkv9pLR5zS0Jm+ZFYmFr/qp4QIuK7KPDlZQLVKMAwYSe8poi1lzEI\r\n"
         		+ " WgIhqJIl+SEMSVi2xGEW/kpdm4JxR4NXu18XJAkLMX5NY70DODMHrYqJyjBC7oCM5x5YoYlyBZn+EC9jFv8o3gwgu7UCfpWdna+absVjOEM1ufHseo7vxXI\r\n"
         		+ " GgyJRvnGKX3j5cO4FJylqP5s1Lr/Wg3L3ZUTSrwCqf2AzhShdydFnxAxFEP6yTY4=</HSH>";      
         QRCodeWriter qrCodeWriter = new QRCodeWriter();
         BitMatrix bitMatrix = qrCodeWriter.encode(xmlData, BarcodeFormat.QR_CODE, 200, 200);

         ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
         MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
         byte[] qrCodeImage = pngOutputStream.toByteArray();
     	System.out.println("qrCodeImage"+qrCodeImage);
        // 3. Créer un PDF contenant le code QR
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, pdfOutputStream);
        document.open();
        System.out.println("document"+document.toString());
        // Ajouter l'image du QR code au PDF
        Image qrImage = Image.getInstance(qrCodeImage);
        qrImage.scaleToFit(200, 200); // Redimensionner l'image si nécessaire
        document.add(qrImage);

        document.close();
        System.out.println("document pages"+document.getPageNumber());
        // 4. Renvoyer le PDF en tant que fichier téléchargeable
        byte[] pdfBytes = pdfOutputStream.toByteArray();
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        String fileName = savedFormData.getNomPrenom().replace(" ", "_") + ".pdf"; 
        System.out.println("fileName"+fileName);
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);    }
    
   
    
    
    
    
    
    
    
    // Méthodes CRUD pour les données
    @PostMapping("/add")
    public FormData addFormData(@RequestBody FormData formData) {
        return formDataService.addFormData(formData);
    }

    @GetMapping("/{matricule}")
    public FormData getEmpByMat(@PathVariable("matricule") String matricule) {
        return formDataService.getEmp(matricule);
    }

    @PutMapping
    public FormData updateEmp(@RequestBody FormData formdata) {
        return formDataService.updateEmp(formdata);
    }
    @RequestMapping(method=RequestMethod.GET)
	List <FormData> getAllMedicaments(){
		return formDataService.getAllEmployees();
		
		
	}
    
    @RequestMapping(value="/{matricule}",method = RequestMethod.DELETE)
	public void deleteByMatricule(@PathVariable("matricule") String matricule)
	{
	formDataService.deleteEmployeeByMatricule(matricule);
	}
}

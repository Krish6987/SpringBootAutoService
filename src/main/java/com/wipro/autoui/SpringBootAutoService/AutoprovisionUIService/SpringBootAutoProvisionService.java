package com.wipro.autoui.SpringBootAutoService.AutoprovisionUIService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
public class SpringBootAutoProvisionService {
	
	
	@GetMapping("/test")
	public String sample() {
		return "Microservice is working!!!";
	}

	@GetMapping("/install/{tool}/{ip_address}/{password}")
	public int installTool(@PathVariable String tool, @PathVariable String ip_address, @PathVariable String password) {
		
		ProcessBuilder processBuilder = new ProcessBuilder();
		int errorCode = 127;
		System.out.println("sshpass -p '"+password+"' ssh-copy-id root@"+ip_address+" -o StrictHostKeyChecking=no");
		System.out.println("ansible-playbook /home/ansadmin/"+tool+".yml -i "+ip_address+", -e 'target="+ip_address+"'");
		processBuilder.command("bash", "-c", "sshpass -p '"+password+"' ssh-copy-id "+ip_address+" -o StrictHostKeyChecking=no" );
     		//processBuilder.command("bash", "-c", "ansible-playbook /home/ansadmin/"+tool+".yml -i "+ip_address+", -e 'target="+ip_address+"'" );
		try {
		    Process process = processBuilder.start();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		    String line;
		    while ((line = reader.readLine()) != null) {
			System.out.println(line);
		    }
		    errorCode = process.waitFor();
		    System.out.println("\nExited with error code : " + errorCode);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}		
			return errorCode;
	}
}

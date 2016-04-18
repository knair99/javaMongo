package com.nimma.jersey.server;



import java.math.BigInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.nimma.jersey.server.eccdatum.ECCDatum;





@Path("/security")
public class Security {
		
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postData(ECCDatum data) {
				
		
		String result = "Hello client: " + " : Your signed message is: " + data.getmessage();
		
		EllipticServer e = new EllipticServer();
		BigInteger[] signature_client = new BigInteger[2];
		BigInteger[] public_key_client = new BigInteger[2];
		
		//Generate server side private key and public key pairs
		e.set_private_key(new BigInteger("82789062540303336671629556971861355318538781871393879428414431259789600936815"));
		e.generate_public_key();
		e.display_data();
		
		signature_client[0] = data.getsignx();
		signature_client[1] = data.getsignature();
		
		public_key_client[0] = data.get_public_key_x();
		public_key_client[1] = data.get_public_key_y();
		
		
		if (e.verify_signature(signature_client, data.gethash(), public_key_client)){
			
			result += "\nClient verified from signature! Authenticity established";
		}
		else{
			
			result += "\nClient could not be verified from signature! Please generate a valid digital signature for authentication";
		}
		
		
		
		return Response.status(201).entity(result).build();
		
	}
	
	@POST
	@Path("/encrypt")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response decryptData(ECCDatum data) throws Exception {
				
		
		String result = "Hello client: " + " : Your cipher message is: " + data.getmessage();
		
		EllipticServer e = new EllipticServer();
		BigInteger[] signature_client = new BigInteger[2];
		BigInteger[] public_key_client = new BigInteger[2];
		
		//Generate server side private key and public key pairs
		e.set_private_key(new BigInteger("82789062540303336671629556971861355318538781871393879428414431259789600936815"));
		e.generate_public_key();
		e.display_data();
		
		signature_client[0] = data.getsignx();
		signature_client[1] = data.getsignature();
		
		public_key_client[0] = data.get_public_key_x();
		public_key_client[1] = data.get_public_key_y();
		
		//Generate diffie hellman shared key, using client's public key
		//which is common knowledge
		BigInteger[] public_key_third_party = new BigInteger[2];
		public_key_third_party[0] = new BigInteger("86123958339353589454334613954037009250298301442165544159467110006827437489844");
		public_key_third_party[1] = new BigInteger("24886167583395101331704142008829378675881745768490090403994652035580982362733");
		BigInteger[] shared_key = e.generate_diffie_hellman_key(public_key_third_party);
		
		System.out.println("Diffie hellman shared secret key: ");
		System.out.println(shared_key[0]);
		
		
		BigInteger[] shared = e.generate_diffie_hellman_key(public_key_third_party);
		
		//Now decrypt ciphertext into plaintext and send to client to show them we know 
		String skey = shared_key[0].toString();
		
		//Pad the ciphertext out
		String cipherString = data.getmessage();
		
		String symmetric_key = skey.substring(0, Math.min(skey.length(), 16));
		
		String plainText = e.decrypt(cipherString, "RandomInitVector", symmetric_key);
		
		
		if (e.verify_signature(signature_client, data.gethash(), public_key_client)){
			
			result += "Your encrypted text was: " + plainText;
		}
		else{
			
			result += "\nClient could not be verified from signature! Please generate a valid digital signature for authentication";
		}
		
		
		
		return Response.status(201).entity(result).build();
		
	}

}



package com.bridgelabz.usermanagement.utility;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

@Component
public class TokenUtil 
{
	public final String SECRET_TOKEN = "Markos";

	/**
	 * @param id of the user
	 * @return a token in string format after encoding the id
	 */
	public String createToken(Long id) 
	{
	 try 			
		{
			Algorithm algorithm = Algorithm.HMAC256(SECRET_TOKEN);

			String token = JWT.create().withClaim("user_id", id).sign(algorithm);
			
			return token;
		} 
		catch (JWTCreationException e) 
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param token passed by the user
	 * @return id of the user after decoding the token
	 */
	public Long decodeToken(String token) 
	{
		Long userid;

		// for verification algorithm
		Verification verification = null;
		try 
		{
			verification = JWT.require(Algorithm.HMAC256(SECRET_TOKEN));
		}
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		}
		
		JWTVerifier jwtverifier = verification.build();
		
		// to decode token
		DecodedJWT decodedjwt = jwtverifier.verify(token);

		Claim claim = decodedjwt.getClaim("user_id");
		userid = claim.asLong();
		return userid;

	}

}
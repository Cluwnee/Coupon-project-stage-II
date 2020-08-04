package com.example.demo.loginManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.WrongUserDetailsException;
import com.example.demo.facades.AdminFacade;
import com.example.demo.facades.ClientFacade;
import com.example.demo.facades.CompanyFacade;
import com.example.demo.facades.CustomerFacade;

@Service
public class LoginManager {

	@Autowired
	private ConfigurableApplicationContext ctx;

	private LoginManager() {

	}

	public ClientFacade login(String email, String password, ClientType clientType) throws WrongUserDetailsException {

		switch (clientType) {
		case Administrator:
			AdminFacade af = ctx.getBean(AdminFacade.class);
			if (af.login(email, password))
				return af;
			else
				throw new WrongUserDetailsException();

		case Company:
			CompanyFacade cof = ctx.getBean(CompanyFacade.class);
			if (cof.login(email, password))
				return cof;
			else
				throw new WrongUserDetailsException();

		case Customer:
			CustomerFacade cuf = ctx.getBean(CustomerFacade.class);
			if (cuf.login(email, password))
				return cuf;
			else
				throw new WrongUserDetailsException();
		}
		return null;
	}

}

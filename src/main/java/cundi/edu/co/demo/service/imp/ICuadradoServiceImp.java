package cundi.edu.co.demo.service.imp;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cundi.edu.co.demo.service.IPerimetroService;

@Service
@Qualifier("cuadrado")
public class ICuadradoServiceImp implements IPerimetroService{

	@Override
	public int obtener(int lado) {
		return lado*4;
	}
}

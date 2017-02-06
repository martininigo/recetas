package com.amaliacocina.recetas;

import org.springframework.beans.factory.annotation.Autowired;

import com.amaliacocina.recetas.domain.Insumo;
import com.amaliacocina.recetas.percistence.dao.InsumoDAO;

public class Insumos {

	@Autowired
	InsumoDAO insumoDAO;

	public static void main(String[] args) {

//		HibernateUtil.buildSessionFactory();
//
//		try {
//			HibernateUtil.openSessionAndBindToThread();
//			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			Insumo insumo = new Insumo("Harina 0000", "Harina 0000", 1000, 25.50);
//			session.save(insumo);
//		} finally {
//			HibernateUtil.closeSessionAndUnbindFromThread();
//		}
//		HibernateUtil.closeSessionFactory();

	}
}
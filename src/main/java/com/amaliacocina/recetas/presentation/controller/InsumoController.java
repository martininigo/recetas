package com.amaliacocina.recetas.presentation.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.amaliacocina.recetas.domain.Insumo;
import com.amaliacocina.recetas.json.JsonTransformer;
import com.amaliacocina.recetas.percistence.dao.BussinessException;
import com.amaliacocina.recetas.percistence.dao.BussinessMessage;
import com.amaliacocina.recetas.percistence.dao.InsumoDAO;

@Controller
public class InsumoController {

	@Autowired
	private JsonTransformer jsonTransformer;

	@Autowired
	InsumoDAO insumoDAO;

	@RequestMapping(value = "/prueba")
	public void prueba(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse) throws IOException {

		Insumo insumo = new Insumo("Azucar", "Azucar", 100, 13.0);
		httpServletResponse.getWriter().println(jsonTransformer.toJson(insumo) + "AAA");
	}

	@RequestMapping(value = "/insumo/{id}", method = RequestMethod.GET, produces = "application/json")
	public void read(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			@PathVariable("id") int id) {
		try {
			Insumo insumo = insumoDAO.get(id);
			String output = jsonTransformer.toJson(insumo);
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
			httpServletResponse.setContentType("application/json; charset=UTF-8");
			httpServletResponse.getWriter().println(output);
		} catch (BussinessException ex) {
			Set<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
			String output = jsonTransformer.toJson(bussinessMessage);

			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			httpServletResponse.setContentType("application/json; charset=UTF-8");
			try {
				httpServletResponse.getWriter().println(output);
			} catch (IOException ex1) {
				Logger.getLogger(InsumoController.class.getName()).log(Level.SEVERE, null, ex1);
			}
		} catch (Exception ex) {
			httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/insumo", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void insert(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			@RequestBody String body) {
		try {
			Insumo insumo = (Insumo) jsonTransformer.fromJSON(body, Insumo.class);
			insumoDAO.saveOrUpdate(insumo);
			String output = jsonTransformer.toJson(insumo);
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
			httpServletResponse.setContentType("application/json; charset=UTF-8");
			httpServletResponse.getWriter().println(output);
		} catch (BussinessException ex) {
			Set<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
			String output = jsonTransformer.toJson(bussinessMessage);

			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			httpServletResponse.setContentType("application/json; charset=UTF-8");
			try {
				httpServletResponse.getWriter().println(output);
			} catch (IOException ex1) {
				Logger.getLogger(InsumoController.class.getName()).log(Level.SEVERE, null, ex1);
			}
		} catch (Exception ex) {
			httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/insumo/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public void update(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			@RequestBody String jsonEntrada, @PathVariable("id") int id) {
		try {
			Insumo insumo = (Insumo) jsonTransformer.fromJSON(jsonEntrada, Insumo.class);
			insumoDAO.saveOrUpdate(insumo);
			String output = jsonTransformer.toJson(insumo);

			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
			httpServletResponse.setContentType("application/json; charset=UTF-8");
			httpServletResponse.getWriter().println(output);
		} catch (BussinessException ex) {
			Set<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
			String output = jsonTransformer.toJson(bussinessMessage);

			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			httpServletResponse.setContentType("application/json; charset=UTF-8");
			try {
				httpServletResponse.getWriter().println(output);
			} catch (IOException ex1) {
				Logger.getLogger(InsumoController.class.getName()).log(Level.SEVERE, null, ex1);
			}

		} catch (Exception ex) {
			httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/insumo/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public void delete(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("id") int id) {
	    try {
	        insumoDAO.delete(id);
	         
	        httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
	        
		} catch (BussinessException ex) {
	        Set<BussinessMessage> bussinessMessage=ex.getBussinessMessages();
	        String output = jsonTransformer.toJson(bussinessMessage);
	         
	        httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        httpServletResponse.setContentType("application/json; charset=UTF-8");
	        try {
	            httpServletResponse.getWriter().println(output);
	        } catch (IOException ex1) {
	            Logger.getLogger(InsumoController.class.getName()).log(Level.SEVERE, null, ex1);
	        }
	         
	    } catch (Exception ex) {
	        httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
	}
	
	@RequestMapping(value = "/insumo", method = RequestMethod.GET, produces = "application/json")
	public void find(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		try {
			List<Insumo> segurosMedicos = insumoDAO.findAll();
			String output = jsonTransformer.toJson(segurosMedicos);

			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
			httpServletResponse.setContentType("application/json; charset=UTF-8");
			httpServletResponse.getWriter().println(output);
		} catch (BussinessException ex) {
			Set<BussinessMessage> bussinessMessage = ex.getBussinessMessages();
			String output = jsonTransformer.toJson(bussinessMessage);

			httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			httpServletResponse.setContentType("application/json; charset=UTF-8");
			try {
				httpServletResponse.getWriter().println(output);
			} catch (IOException ex1) {
				Logger.getLogger(InsumoController.class.getName()).log(Level.SEVERE, null, ex1);
			}
		} catch (Exception ex) {
			httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
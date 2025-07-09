package com.algonquin.aep.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.algonquin.aep.dao.AcademicInstitutionDAO;
import com.algonquin.aep.dao.AcademicInstitutionDAOImpl;
import com.algonquin.aep.dto.AcademicInstitutionDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/updateInstitutionAddress")
public class UpdateInstitutionAddressServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UpdateInstitutionAddressServlet.class);
    private AcademicInstitutionDAO institutionDAO;

    @Override
    public void init() throws ServletException {
        institutionDAO = new AcademicInstitutionDAOImpl();
        logger.info("UpdateInstitutionAddressServlet initialized");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int institutionId = (int) session.getAttribute("userId");
        String address = request.getParameter("address");
        logger.info("Updating address for institution ID: {}", institutionId);

        AcademicInstitutionDTO institution = institutionDAO.findInstitutionById(institutionId);
        if (institution != null) {
            institution.setAddress(address);
            institutionDAO.updateInstitution(institution);
            logger.info("Address updated successfully for institution ID: {}", institutionId);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            logger.warn("Institution not found for ID: {}", institutionId);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

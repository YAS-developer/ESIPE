package filtres;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// On définit ici les URL à protéger (le Servlet ET la JSP)
@WebFilter(urlPatterns = { "/GestionOperations", "/JOperations.jsp" })
public class FAuthentification implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {
       
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); // false = ne pas créer si elle n'existe pas

        // 1. Est-on déjà connecté ? (Le Bean existe-t-il en session ?)
        boolean estConnecte = (session != null && session.getAttribute("monBean") != null);

        // 2. Est-ce une tentative de connexion ? (Paramètre NoDeCompte présent dans l'URL)
        String noDeCompteParam = request.getParameter("NoDeCompte");
        boolean demandeConnexion = (noDeCompteParam != null && !noDeCompteParam.isEmpty());

        if (estConnecte || demandeConnexion) {
            // C'est bon, on laisse passer la requête
            chain.doFilter(request, response);
        } else {
            // Ni connecté, ni en train de se connecter -> Accès interdit
            // On renvoie une erreur 403
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès interdit : Veuillez vous identifier via la page de connexion (ou l'URL avec paramètre pour l'instant).");
        }
    }

    public void destroy() {
        // Nettoyage
    }
}
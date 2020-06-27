package tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class TagCursoJsp extends SimpleTagSupport{
		
	//Personalizar tag
	@Override
	public void doTag() throws JspException, IOException {
		super.doTag();
		JspWriter out = getJspContext().getOut();
		out.print("Tag jsp customizada");
	}
}

package bi.colegios.ui.data.table;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import bi.colegios.bean.Usuario;
import bi.colegios.dao.UsuarioDao;

public class UsuarioLazyDataModel extends LazyDataModel<Usuario> {
	private static final long serialVersionUID = 1L;
	private UsuarioDao usuarioDao;
	
	public UsuarioLazyDataModel (UsuarioDao usuarioDao) {
		super();
		this.usuarioDao = usuarioDao;
	}

	@Override
	public List<Usuario> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		this.setRowCount(usuarioDao.countUsuarios());
		return usuarioDao.listUsuariosPaginado(first, pageSize);
	}
}

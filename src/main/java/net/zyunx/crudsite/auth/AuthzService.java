package net.zyunx.crudsite.auth;

/**
 * 鉴权服务
 * @author zy-t193
 *
 */
public interface AuthzService {
	boolean hasPermission(String username, String permission);
}

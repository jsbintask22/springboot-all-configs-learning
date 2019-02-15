package cn.jsbintask.springbootallconfigs.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jsbintask@gmail.com
 * @date 2019/2/15 17:10
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}

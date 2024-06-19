package com.example.managementsystem.mappers;

import com.example.managementsystem.DTO.UserDTO;
import com.example.managementsystem.models.entities.User;
import com.example.managementsystem.models.entities.UserRole;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-13T13:46:44+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        Set<String> roles = null;
        Long matricule = null;
        String nom = null;
        String prenom = null;
        String emailpersonnel = null;
        String email = null;
        String tel = null;
        String adresse = null;
        String departement = null;
        String civilite = null;

        roles = roleToString( user.getRoles() );
        matricule = user.getMatricule();
        nom = user.getNom();
        prenom = user.getPrenom();
        emailpersonnel = user.getEmailpersonnel();
        email = user.getEmail();
        tel = user.getTel();
        adresse = user.getAdresse();
        departement = user.getDepartement();
        civilite = user.getCivilite();

        UserDTO userDTO = new UserDTO( matricule, nom, prenom, emailpersonnel, email, tel, adresse, departement, civilite, roles );

        return userDTO;
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setRoles( stringToRole( userDTO.roles() ) );
        user.setMatricule( userDTO.matricule() );
        user.setNom( userDTO.nom() );
        user.setPrenom( userDTO.prenom() );
        user.setEmailpersonnel( userDTO.emailpersonnel() );
        user.setEmail( userDTO.email() );
        user.setTel( userDTO.tel() );
        user.setAdresse( userDTO.adresse() );
        user.setDepartement( userDTO.departement() );
        user.setCivilite( userDTO.civilite() );

        return user;
    }

    @Override
    public void updateUserFromDTO(User user, UserDTO userDTO) {
        if ( userDTO == null ) {
            return;
        }

        user.setNom( userDTO.nom() );
        user.setPrenom( userDTO.prenom() );
        user.setEmailpersonnel( userDTO.emailpersonnel() );
        user.setEmail( userDTO.email() );
        user.setTel( userDTO.tel() );
        user.setAdresse( userDTO.adresse() );
        user.setDepartement( userDTO.departement() );
        user.setCivilite( userDTO.civilite() );
        if ( user.getRoles() != null ) {
            Set<UserRole> set = stringToRole( userDTO.roles() );
            if ( set != null ) {
                user.getRoles().clear();
                user.getRoles().addAll( set );
            }
            else {
                user.setRoles( null );
            }
        }
        else {
            Set<UserRole> set = stringToRole( userDTO.roles() );
            if ( set != null ) {
                user.setRoles( set );
            }
        }
        user.setMatricule( userDTO.matricule() );
    }

    @Override
    public List<UserDTO> toDTOs(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( users.size() );
        for ( User user : users ) {
            list.add( toDTO( user ) );
        }

        return list;
    }
}

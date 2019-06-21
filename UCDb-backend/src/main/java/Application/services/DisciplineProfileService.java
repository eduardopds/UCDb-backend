package Application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import Application.model.DisciplineProfile;
import Application.model.User;
import Application.repositoriesDAO.DisciplineProfileDAO;
import Application.repositoriesDAO.GradeDAO;
import Application.repositoriesDAO.UserDAO;

@Service
public class DisciplineProfileService {

	private final DisciplineProfileDAO disciplineProfileDAO;
	private final UserDAO userDAO;
	private GradeDAO gradeDAO;

	public DisciplineProfileService(DisciplineProfileDAO disciplineProfileDAO, GradeDAO gradeDAO,UserDAO userDAO) {
		this.gradeDAO = gradeDAO;
		this.disciplineProfileDAO = disciplineProfileDAO;
		this.userDAO = userDAO;
	}

	public Iterable<DisciplineProfile> create(Iterable<DisciplineProfile> disciplineProfile) {

		return disciplineProfileDAO.saveAll(disciplineProfile);
	}

	public DisciplineProfile getProfile(long id) {
		return disciplineProfileDAO.findById(id);
	}

	public Double getAverageProfile(Long id) {
		double sum = 0;
		double average = 0;

		List<Long> grades = gradeDAO.findGradesByPerfil(id);
		for (Long grade : grades) {
			sum += grade;
		}
		if (grades.size() > 0) {
			average = sum / grades.size();
		}
		return average;

	}

	public List<DisciplineProfile> findBySubstring(String substring) {
		return disciplineProfileDAO.findBySubstring(substring);
	}
	/*
	 * Dar like
	 */
	public DisciplineProfile toLike(String email,long idProfile) {
		
		User user = userDAO.findByLogin(email);
		DisciplineProfile discipline = disciplineProfileDAO.findById(idProfile);
		
		discipline.getUserThatGaveLike().add(user);
		return disciplineProfileDAO.save(discipline);
		
		
	}

}


/**
 * @author mshalaby20
 *
 *
 * Singleton Class to ensure there's only 1 level manager
 * 
 */
public class LevelManager {

	private Level l;

	private static LevelManager instance = new LevelManager();

	public static LevelManager getInstance() {
		return instance;
	}

	private LevelManager() {
		this.l = Level.EASY;
	}

	public Level getLevel() {
		return l;
	}

	public void setLevel(Level l) {
		this.l = l;
	}

}

package example.threadex.entity;

import jakarta.persistence.*;

@Entity
@Table(name="winusers")
public class Winusers {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "full_name", nullable = true, length = 20)
    private String fullName;
    @Basic
    @Column(name = "win_count", nullable = true)
    private Integer winCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getWinCount() {
        return winCount;
    }

    public void setWinCount(Integer winCount) {
        this.winCount = winCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Winusers winusers = (Winusers) o;

        if (id != winusers.id) return false;
        if (fullName != null ? !fullName.equals(winusers.fullName) : winusers.fullName != null) return false;
        if (winCount != null ? !winCount.equals(winusers.winCount) : winusers.winCount != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (winCount != null ? winCount.hashCode() : 0);
        return result;
    }
}

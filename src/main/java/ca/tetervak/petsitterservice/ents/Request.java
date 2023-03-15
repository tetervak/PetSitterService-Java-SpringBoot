package ca.tetervak.petsitterservice.ents;

import ca.tetervak.petsitterservice.base.RequestStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by iuliana.cosmina on 2/7/16.
 */
@Entity
@Table(name="P_REQUEST")
public class Request extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    /**
     * This field is used for marking the beginning of the interval when a pet sitter is needed.
     */
    @JsonIgnore
    @Column(name = "START_AT", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startAt;

    /**
     * This field is used for marking the end of the interval when a pet sitter is needed.
     */
    @JsonIgnore
    @Column(name = "END_AT", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "REQUEST_STATUS")
    private RequestStatus requestStatus;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="P_REQUEST_PET",
            joinColumns = {@JoinColumn(name="REQUEST_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name="PET_ID", referencedColumnName = "ID")})
    private Set<Pet> pets = new HashSet<>();

    @Size(max = 500)
    @NotEmpty
    private String details;

    @JsonIgnore
    @OneToMany(mappedBy = "request", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Response> responses = new HashSet<>();

    //required by JPA
    public Request() {
        super();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public boolean addPet(Pet pet) {
        return pets.add(pet);
    }

    protected void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Set<Response> getResponses() {
        return responses;
    }

    protected void setResponses(Set<Response> responses) {
        this.responses = responses;
    }

    public boolean addResponse(Response response) {
        response.setRequest(this);
        return responses.add(response);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Request request = (Request) o;

        if (user != null ? !user.getId().equals(request.user.getId()) : request.user != null) return false;
        if (startAt != null ? !startAt.equals(request.startAt) : request.startAt != null) return false;
        if (endAt != null ? !endAt.equals(request.endAt) : request.endAt != null) return false;
        if (requestStatus != request.requestStatus) return false;
        return pets != null ? pets.equals(request.pets) : request.pets == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (startAt != null ? startAt.hashCode() : 0);
        result = 31 * result + (endAt != null ? endAt.hashCode() : 0);
        result = 31 * result + (requestStatus != null ? requestStatus.hashCode() : 0);
        result = 31 * result + (pets != null ? pets.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("Request[id='%,.2f', user='%s', startAt='%s', requestStatus='%s', requestStatus='%s']",
                id, user == null ? "" : user.getId(), sdf.format(startAt), sdf.format(endAt), pets);
    }
}

package com.example.model;
 
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
 
import java.util.List;
 
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelPackage {
 
    

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packageId;
 
    @NotBlank(message = "Title is mandatory")
    private String title;
 
    @NotBlank(message = "Description is mandatory")
    private String description;
 
    @Min(value = 1, message = "Duration should be at least 1 day")
    private int duration;
 
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private double price;
 
    @NotNull(message = "Highlights cannot be null")
    private List<@NotBlank(message = "Highlight item cannot be blank") String> highlights;
 
    @OneToMany(cascade = CascadeType.ALL)
    @NotNull(message = "Flights must not be null")
    private List<@Valid Flight> flights;
 
    @OneToMany(cascade = CascadeType.ALL)
    @NotNull(message = "Hotels must not be null")
    private List<@Valid Hotel> hotels;
 
    @OneToMany(cascade = CascadeType.ALL)
    @NotNull(message = "Sightseeing must not be null")
    private List<@Valid Sightseeing> sightseeing;
 
    @OneToMany(cascade = CascadeType.ALL)
    @NotNull(message = "Itinerary must not be null")
    private List<@Valid Itinerary> itinerary;
 
    @Embedded
    @Valid
    private Offer offer;
    
    

	
}
 
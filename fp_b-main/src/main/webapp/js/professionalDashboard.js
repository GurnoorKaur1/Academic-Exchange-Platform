$(document).ready(function() {
    // Handle sidebar navigation
    $('.nav-link').on('click', function(e) {
        e.preventDefault();
        var target = $(this).attr('href');
        $('.nav-link').removeClass('active');
        $(this).addClass('active');
        $('div[id]').hide();
        $(target).show();
    });

    // Fetch and populate professional profile
    function fetchProfessionalProfile() {
        $.ajax({
            url: 'getProfessionalProfile',
            method: 'GET',
            success: function(response) {
                $('#name').val(response.name);
                $('#email').val(response.email);
                $('#type').val(response.type);
                $('#creationDate').val(response.creationDate);
                $('#currentPosition').val(response.currentPosition);
                $('#currentInstitution').val(response.currentInstitution);
                $('#educationBackground').val(response.educationBackground);
                $('#areaOfExpertise').val(response.areaOfExpertise);
            },
            error: function(xhr, status, error) {
                console.error('Error fetching professional profile:', error);
            }
        });
    }

    // Update professional profile
    function updateProfessionalProfile() {
        $('#profileForm').submit(function(e) {
            e.preventDefault();
            var formData = {
                name: $('#name').val(),
                currentPosition: $('#currentPosition').val(),
                currentInstitution: $('#currentInstitution').val(),
                educationBackground: $('#educationBackground').val(),
                areaOfExpertise: $('#areaOfExpertise').val()
            };

            $.ajax({
                url: 'api/professional-profile',
                type: 'PUT',
                data: JSON.stringify(formData),
                contentType: 'application/json',
                success: function(response) {
                    alert('Profile updated successfully');
                },
                error: function(xhr, status, error) {
                    if (xhr.status === 200) {
                        // Treat as success if status is 200
                        alert('Profile updated successfully');
                    } else {
                        alert('Error updating profile: ' + (xhr.responseJSON?.error || 'Unknown error occurred'));
                    }
                }
            });
        });
    }

    // Initialize profile functionality
    fetchProfessionalProfile();
    updateProfessionalProfile();

    // Handle course search form submission
    $('#searchCourseForm').on('submit', function(e) {
        e.preventDefault();
        // Add AJAX call to submit search criteria and populate results
        console.log('Course search form submitted');
        $('#searchResults').show();
        populateSearchResults();
    });

    // Handle "Request to Teach" button click
    $('#requestToTeach').on('click', function() {
        var selectedCourses = $('.course-checkbox:checked');
        if (selectedCourses.length > 0) {
            // Add AJAX call to submit teaching requests
            console.log('Request to teach submitted for ' + selectedCourses.length + ' courses');
        } else {
            alert('Please select at least one course to request.');
        }
    });

    $('#selectAll').on('change', function() {
        $('.course-checkbox').prop('checked', this.checked);
    });

    // Initial population of notifications
    populateNotifications();

    // Helper functions
    function populateSearchResults() {
        // Add AJAX call to fetch and populate search results
        console.log('Populating search results');
    }

    function populateNotifications() {
        // Add AJAX call to fetch and populate notifications
        console.log('Populating notifications');
    }
});

$(document).ready(function() {
    // Handle sidebar navigation
    $('.nav-link').on('click', function(e) {
        e.preventDefault();
        var target = $(this).attr('href');
        $('.nav-link').removeClass('active');
        $(this).addClass('active');
        $('div[id]').hide();
        // $(target).show();
        $("#profileCreation, #courseManagement, #courseRequests").hide();

        if (target === "#createCourse") {
            $("#courseManagement").show();
            $("#createCourse").show();
            $("#editCourses").hide();
        } else if (target === "#editCourses") {
            $("#courseManagement").show();
            $("#createCourse").hide();
            $("#editCourses").show();
        } else {
            $(target).show();
        }
    });

    $("#courseManagement").on("click", "a[href='#createCourse']", function(e) {
        e.preventDefault();
        $("#createCourse").show();
        $("#editCourses").hide();
    });

    $("#courseManagement").on("click", "a[href='#editCourses']", function(e) {
        e.preventDefault();
        $("#editCourses").show();
        $("#createCourse").hide();
    });

    // Handle form submissions
    $('#profileForm').on('submit', function(e) {
        e.preventDefault();
        // Add AJAX call to submit form data
    });

    $('#createCourseForm').on('submit', function(e) {
        e.preventDefault();
        $.ajax({
            url: $(this).attr('action'),
            type: 'POST',
            data: $(this).serialize(),
            success: function(response) {
                console.log('Course created successfully:', response);
                populateCourseOfferings();
                alert("The course created successfully.")
            },
            error: function(xhr, status, error) {
                console.error('Error creating course:', error);
                alert('There was an error creating the course. Please try again.');
            }
        });
    });

    // Handle course request actions
    $('#acceptSelected').on('click', function() {
        // Add logic to accept selected requests
    });

    $('#rejectSelected').on('click', function() {
        // Add logic to reject selected requests
    });

    $('#selectAll').on('change', function() {
        $('.request-checkbox').prop('checked', this.checked);
    });

    // Populate course list and request list (example)
    function populateCourseList() {
        $.ajax({
            url: 'getCourses',
            method: 'GET',
            success: function(courses) {
                var courseList = $('#currentOfferingsList');
                courseList.empty();
                courses.forEach(function(course) {
                    courseList.append('<tr><td>' + course.title + '</td><td>' + course.term + '</td><td>' + course.code + '</td></tr>');
                });
            },
            error: function(xhr, status, error) {
                console.error('Error fetching courses:', error);
            }
        });
    }

    function populateRequestList() {
        // Add AJAX call to get request data and populate #requestList
    }

    populateCourseList();
    populateRequestList();

    // Call the function when the page loads
    populateCourseOfferings();

    function fetchInstitutionAddress() {
        $.ajax({
            url: 'getInstitutionAddress',
            method: 'GET',
            success: function(response) {
                if (response.address) {
                    $('#address').val(response.address);
                }
            },
            error: function(xhr, status, error) {
                console.error('Error fetching institution address:', error);
            }
        });
    }

    function updateInstitutionAddress() {
        $('#profileForm').submit(function(e) {
            e.preventDefault();
            var address = $('#address').val();
            $.ajax({
                url: 'updateInstitutionAddress',
                method: 'POST',
                data: { address: address },
                success: function(response) {
                    alert('Profile updated successfully');
                },
                error: function(xhr, status, error) {
                    console.error('Error updating institution address:', error);
                    alert('Failed to update profile');
                }
            });
        });
    }

    function fetchInstitutionProfile() {
        $.ajax({
            url: 'getInstitutionProfile',
            method: 'GET',
            success: function(response) {
                $('#name').val(response.name);
                $('#email').val(response.email);
                $('#type').val(response.type);
                $('#creationDate').val(response.creationDate);
                $('#address').val(response.address);
            },
            error: function(xhr, status, error) {
                console.error('Error fetching institution profile:', error);
            }
        });
    }

    fetchInstitutionAddress();
    updateInstitutionAddress();
    fetchInstitutionProfile();



    $('#saveChanges').on('click', function() {
        $.ajax({
            url: 'updateCourse', // The endpoint to update course
            method: 'POST',
            data: $('#editCourseForm').serialize(),
            success: function(response) {
                $('#editCourseModal').modal('hide');
                populateCourseOfferings(); // Refresh the course offerings
            },
            error: function(xhr, status, error) {
                console.error('Error updating course:', error);
                alert('There was an error updating the course. Please try again.');
            }
        });
    });
});

function editCourse(courseId) {
    // Make an AJAX request to get course details by courseId
    $.ajax({
        url: 'getCourseDetails?courseId=' + courseId, // Adjust endpoint as necessary
        method: 'GET',
        success: function(course) {
            // Populate the modal with course details
            $('#courseId').val(course.courseId);
            $('#editCourseTitle').val(course.title);
            $('#editCourseCode').val(course.code);
            $('#editTerm').val(course.term);
            $('#editOutline').val(course.outline);
            $('#editSchedule').val(course.schedule);
            $('#editQualifications').val(course.qualifications);
            $('#editDeliveryMethod').val(course.deliveryMethod);
            $('#editCompensation').val(course.compensation);
            $('#editQualifications').val(course.preferredQualifications);
            // Show the modal
            $('#editCourseModal').modal('show');
        },
        error: function(xhr, status, error) {
            console.error('Error fetching course details:', error);
        }
    });
}

// Function to populate course offerings
function populateCourseOfferings() {
    $.ajax({
        url: 'getCourseOfferings', // This should be the endpoint to fetch course offerings
        method: 'GET',
        success: function(courses) {
            var courseList = $('#currentOfferingsList');
            courseList.empty();
            courses.forEach(function(course) {
                courseList.append('<tr>' +
                    '<td>' + course.title + '</td>' +
                    '<td>' + course.term + '</td>' +
                    '<td>' + course.code + '</td>' +
                    '<td><button class="btn-primary" onclick="editCourse(' + course.courseId + ');">Edit</td>' +
                    '</tr>');
            });
        },
        error: function(xhr, status, error) {
            console.error('Error fetching course offerings:', error);
        }
    });
}
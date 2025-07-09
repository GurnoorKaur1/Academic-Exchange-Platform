<%--
  Created by IntelliJ IDEA.
  User: cbc
  Date: 2024-10-23
  Time: 2:46 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="profileCreation">
    <h2>Institution Profile</h2>
    <form id="profileForm">
        <div class="form-group">
            <label for="name">Institution Name</label>
            <input type="text" class="form-control" id="name" name="name" readonly>
        </div>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" name="email" readonly>
        </div>
        <div class="form-group">
            <label for="type">Institution Type</label>
            <input type="text" class="form-control" id="type" name="type" readonly>
        </div>
        <div class="form-group">
            <label for="creationDate">Account Creation Date</label>
            <input type="text" class="form-control" id="creationDate" name="creationDate" readonly>
        </div>
        <div class="form-group">
            <label for="address">Institution Address</label>
            <input type="text" class="form-control" id="address" name="address" placeholder="Please enter institution address" required>
        </div>
        <%-- Current Course Offerings section commented out
        <!--div class="form-group">
            <label for="currentOfferings">Current Course Offerings</label>
            <table class="table table-striped table-sm">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Term</th>
                        <th>Code</th>
                    </tr>
                </thead>
                <tbody id="currentOfferingsList">

                </tbody>
            </table>
        </div -->
        --%>
        <div class="text-right">
            <button type="submit" class="btn btn-primary">Save Profile</button>
        </div>
    </form>
</div>

<div id="courseManagement" style="display:none;">
    <div id="createCourse">
        <h2>Create Course</h2>
        <form id="createCourseForm" action="createCourse" method="post">
            <div class="form-group">
                <label for="courseTitle">Course Title</label>
                <input type="text" class="form-control" id="courseTitle" name="courseTitle" required>
            </div>
            <div class="form-group">
                <label for="courseCode">Course Code</label>
                <input type="text" class="form-control" id="courseCode" name="courseCode" required>
            </div>
            <div class="form-group">
                <label for="term">Term</label>
                <input type="text" class="form-control" id="term" name="term" required>
            </div>
            <div class="form-group">
                <label for="courseOutline">Course Outline</label>
                <textarea class="form-control" id="courseOutline" name="courseOutline" rows="3" required></textarea>
            </div>
            <div class="form-group">
                <label for="schedule">Schedule</label>
                <input type="text" class="form-control" id="schedule" name="schedule" required>
            </div>
            <div class="form-group">
                <label for="qualifications">Preferred Qualifications</label>
                <textarea class="form-control" id="qualifications" name="qualifications" rows="3" required></textarea>
            </div>
            <div class="form-group">
                <label for="deliveryMethod">Delivery Method</label>
                <select class="form-control" id="deliveryMethod" name="deliveryMethod" required>
                    <option value="in-person">In-person</option>
                    <option value="remote">Online</option>
                    <option value="hybrid">Hybrid</option>
                </select>
            </div>
            <div class="form-group">
                <label for="compensation">Compensation</label>
                <input type="number" class="form-control" id="compensation" name="compensation" required>
            </div>
            <button type="submit" class="btn btn-primary">Create Course</button>
        </form>
    </div>

    <div id="editCourses" style="display:none;">
        <h2>Edit Courses</h2>
        <table class="table">
            <thead>
                <tr>
                    <th>Course Title</th>
                    <th>Course Code</th>
                    <th>Term</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody id="currentOfferingsList">
                <!-- Course list will be populated dynamically -->
            </tbody>
        </table>
    </div>
</div>

<div id="courseRequests" style="display:none;">
    <h2>Course Requests</h2>
    <table class="table">
        <thead>
            <tr>
                <th>Course Code</th>
                <th>Course Title</th>
                <th>Professional</th>
                <th>Status</th>
                <th>Created At</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody id="requestList">
            <!-- Request list will be populated dynamically -->
        </tbody>
    </table>
</div>

<!-- Edit Course Modal -->
<div class="modal fade" id="editCourseModal" tabindex="-1" aria-labelledby="editCourseModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editCourseModalLabel">Edit Course</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span>&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="editCourseForm">
                    <input type="hidden" id="courseId" name="courseId">
                    <div class="form-group">
                        <label for="editCourseTitle">Course Title</label>
                        <input type="text" class="form-control" id="editCourseTitle" name="courseTitle" required>
                    </div>
                    <div class="form-group">
                        <label for="editCourseCode">Course Code</label>
                        <input type="text" class="form-control" id="editCourseCode" name="courseCode" required>
                    </div>
                    <div class="form-group">
                        <label for="editTerm">Term</label>
                        <input type="text" class="form-control" id="editTerm" name="term" required>
                    </div>
                    <div class="form-group">
                        <label for="editOutline">Course Outline</label>
                        <textarea class="form-control" id="editOutline" name="courseOutline" rows="3" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="editSchedule">Schedule</label>
                        <input type="text" class="form-control" id="editSchedule" name="schedule" required>
                    </div>
                    <div class="form-group">
                        <label for="editQualifications">Preferred Qualifications</label>
                        <textarea class="form-control" id="editQualifications" name="qualifications" rows="3" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="editDeliveryMethod">Delivery Method</label>
                        <select class="form-control" id="editDeliveryMethod" name="deliveryMethod" required>
                            <option value="in-person">In-person</option>
                            <option value="remote">Online</option>
                            <option value="hybrid">Hybrid</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="editCompensation">Compensation</label>
                        <input type="number" class="form-control" id="editCompensation" name="compensation" required>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="saveChanges">Save changes</button>
            </div>
        </div>
    </div>
</div>

<script>
function loadTeachingRequests() {
    $.ajax({
        url: '${pageContext.request.contextPath}/api/teaching-request',
        method: 'GET',
        success: function(requests) {
            var tbody = $('#requestList');
            tbody.empty();
            
            if (!requests || requests.length === 0) {
                tbody.append('<tr><td colspan="6" class="text-center">No requests found</td></tr>');
                return;
            }
            
            requests.forEach(function(request) {
                var row = $('<tr>');
                row.append($('<td>').text(request.courseCode));
                row.append($('<td>').text(request.courseTitle));
                row.append($('<td>').text(request.professionalName));
                row.append($('<td>').text(request.status));
                row.append($('<td>').text(new Date(request.createdAt).toLocaleString()));
                
                if (request.status === 'Pending') {
                    var actions = $('<td>').html(
                        '<button class="btn btn-success btn-sm accept-request" ' +
                        'data-id="' + request.requestId + '">' +
                        '<i class="fas fa-check"></i> Accept</button> ' +
                        '<button class="btn btn-danger btn-sm reject-request" ' +
                        'data-id="' + request.requestId + '">' +
                        '<i class="fas fa-times"></i> Reject</button>'
                    );
                    row.append(actions);
                } else {
                    row.append($('<td>').text('-'));
                }
                
                tbody.append(row);
            });
        },
        error: function(xhr) {
            $('#requestList').html(
                '<tr><td colspan="6" class="text-center text-danger">' +
                'Error loading requests. Please try again later.</td></tr>'
            );
        }
    });
}

function updateTeachingRequest(requestId, status) {
    $.ajax({
        url: '${pageContext.request.contextPath}/api/teaching-request',
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({
            requestId: requestId,
            status: status
        }),
        success: function() {
            // Reload the requests after update
            loadTeachingRequests();
        },
        error: function(xhr) {
            alert('Error updating teaching request: ' + xhr.responseText);
        }
    });
}

$(document).ready(function() {
    // Load teaching requests when page loads
    loadTeachingRequests();

    // Handle accept request
    $(document).on('click', '.accept-request', function() {
        var requestId = $(this).data('id');
        updateTeachingRequest(requestId, 'Accepted');
    });

    // Handle reject request
    $(document).on('click', '.reject-request', function() {
        var requestId = $(this).data('id');
        updateTeachingRequest(requestId, 'Rejected');
    });
});
</script>

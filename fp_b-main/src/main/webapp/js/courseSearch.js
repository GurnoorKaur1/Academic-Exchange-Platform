const API_ENDPOINTS = {
  SEARCH_OPTIONS: 'getSearchOptions',
  SEARCH_COURSE: 'searchCourse'
}

const SELECTORS = {
  INSTITUTION: '#institution',
  COURSE_CODE: '#courseCode',
  COURSE_TITLE: '#courseTitle',
  TERM: '#term',
  SCHEDULE: '#schedule',
  DELIVERY_METHOD: '#deliveryMethod',
  SEARCH_FORM: '#searchCourseForm',
  RESULTS_TABLE: '#searchResultsTable tbody'
}

$(document).ready(function () {
  console.log('Course search initialization started')
  initializeSearchForm()
  bindEventHandlers()
})

function initializeSearchForm() {
  loadInstitutions()
  initializeScheduleOptions()
}

function loadInstitutions() {
  makeAjaxCall({
    url: API_ENDPOINTS.SEARCH_OPTIONS,
    data: { type: 'institutions' },
    success: function (data) {
      console.log('Received institutions:', data)
      populateDropdown(
        SELECTORS.INSTITUTION,
        data,
        'Select Institution',
        'id',
        'name'
      )
    }
  })
}

function initializeScheduleOptions() {
  $(SELECTORS.SCHEDULE).html(`
        <option value="">Select Schedule</option>
        <option value="AM">Morning (AM)</option>
        <option value="PM">Afternoon (PM)</option>
    `)
}

function bindEventHandlers() {
  $(SELECTORS.INSTITUTION).change(handleInstitutionChange)
  $(SELECTORS.COURSE_CODE).change(handleCourseCodeChange)
  $(SELECTORS.SEARCH_FORM).submit(handleFormSubmit)
}

function handleInstitutionChange() {
  const institutionId = $(this).val()
  console.log('Institution selected:', institutionId)

  if (!institutionId) {
    clearDependentDropdowns()
    return
  }

  loadDependentDropdowns(institutionId)
}

function handleCourseCodeChange() {
  const institutionId = $(SELECTORS.INSTITUTION).val()
  const courseCode = $(this).val()

  if (!courseCode) {
    clearCourseRelatedFields()
    return
  }

  loadCourseDetails(institutionId, courseCode)
}

function handleFormSubmit(e) {
  e.preventDefault()
  const searchData = collectFormData()
  performSearch(searchData)
}

function collectFormData() {
  return {
    institutionName: $(SELECTORS.INSTITUTION + ' option:selected').text(),
    courseCode: $(SELECTORS.COURSE_CODE).val(),
    courseTitle: $(SELECTORS.COURSE_TITLE).val(),
    term: $(SELECTORS.TERM).val(),
    schedule: $(SELECTORS.SCHEDULE).val(),
    deliveryMethod: $(SELECTORS.DELIVERY_METHOD).val()
  }
}

function performSearch(searchData) {
  console.log('Search form data:', searchData)
  makeAjaxCall({
    url: API_ENDPOINTS.SEARCH_COURSE,
    method: 'POST',
    data: searchData,
    success: displaySearchResults
  })
}

function displaySearchResults(results) {
  const tbody = $(SELECTORS.RESULTS_TABLE)
  tbody.empty()

  if (results && results.length > 0) {
    results.forEach(function (course) {
      const row = createResultRow(course)
      tbody.append(row)
    })
  } else {
    tbody.append(
      '<tr><td colspan="8" class="text-center">No results found</td></tr>'
    )
  }
}

function createResultRow(course) {
  return $('<tr></tr>').append(`
        <td><input type="checkbox" class="course-checkbox" name="selectedCourses" value="${course.courseId}"></td>
        <td>${course.code}</td>
        <td>${course.title}</td>
        <td>${course.institutionName}</td>
        <td>${course.term}</td>
        <td>${course.schedule}</td>
        <td>${course.deliveryMethod}</td>
        <td><button class="btn btn-primary btn-sm view-details" data-course-id="${course.courseId}">View Details</button></td>
    `)
}

// 辅助函数
function makeAjaxCall({ url, method = 'GET', data, success }) {
  $.ajax({
    url,
    method,
    data,
    success,
    error: function (xhr, status, error) {
      console.error(`Ajax error for ${url}:`, {
        status,
        error,
        response: xhr.responseText
      })
    }
  })
}

function populateDropdown(
  selector,
  data,
  defaultText,
  valueKey = null,
  textKey = null
) {
  const $select = $(selector)
  $select.empty().append($('<option>').val('').text(defaultText))

  if (data && data.length > 0) {
    data.forEach((item) => {
      const value = valueKey ? item[valueKey] : item
      const text = textKey ? item[textKey] : item
      $select.append($('<option>').val(value).text(text))
    })
  }
}

function loadDependentDropdowns(institutionId) {
  ;['courseCodes', 'courseTitles', 'terms'].forEach((type) => {
    makeAjaxCall({
      url: API_ENDPOINTS.SEARCH_OPTIONS,
      data: { type, institutionId },
      success: function (data) {
        const selector =
          type === 'courseCodes'
            ? SELECTORS.COURSE_CODE
            : type === 'courseTitles'
            ? SELECTORS.COURSE_TITLE
            : SELECTORS.TERM
        populateDropdown(selector, data, `Select ${type.slice(0, -1)}`)
      }
    })
  })
}

function clearDependentDropdowns() {
  ;[SELECTORS.COURSE_CODE, SELECTORS.COURSE_TITLE, SELECTORS.TERM].forEach(
    (selector) => {
      $(selector)
        .empty()
        .append(
          $('<option>').val('').text('Please select an institution first')
        )
    }
  )
}

function clearCourseRelatedFields() {
  $(SELECTORS.COURSE_TITLE).val('')
  $(SELECTORS.TERM).empty().append($('<option value="">Select Term</option>'))
}

function loadCourseDetails(institutionId, courseCode) {
  loadCourseTitle(institutionId, courseCode)
  loadCourseTerms(institutionId, courseCode)
}

function loadCourseTitle(institutionId, courseCode) {
  makeAjaxCall({
    url: API_ENDPOINTS.SEARCH_OPTIONS,
    data: {
      type: 'courseTitle',
      institutionId,
      courseCode
    },
    success: function (data) {
      $(SELECTORS.COURSE_TITLE).val(data || '')
    }
  })
}

function loadCourseTerms(institutionId, courseCode) {
  makeAjaxCall({
    url: API_ENDPOINTS.SEARCH_OPTIONS,
    data: {
      type: 'terms',
      institutionId,
      courseCode
    },
    success: function (terms) {
      const $termSelect = $(SELECTORS.TERM)
      $termSelect.empty()

      if (terms && terms.length > 0) {
        if (terms.length === 1) {
          $termSelect.append(
            $('<option>').val(terms[0]).text(terms[0]).prop('selected', true)
          )
        } else {
          populateDropdown(SELECTORS.TERM, terms, 'Select Term')
        }
      } else {
        $termSelect.append($('<option>').val('').text('No terms available'))
      }
    }
  })
}

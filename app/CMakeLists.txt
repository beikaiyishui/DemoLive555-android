# Sets the minimum version of CMake required to build the native
# library. You should either keep the default value or only pass a
# value of 3.4.0 or lower.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds it for you.
# Gradle automatically packages shared libraries with your APK.

set(LIVE555_ROOT_PATH src/main/cpp/live555)

add_definitions(-DXLOCALE_NOT_USED)

include_directories(${LIVE555_ROOT_PATH}/BasicUsageEnvironment/include)
include_directories(${LIVE555_ROOT_PATH}/groupsock/include)
include_directories(${LIVE555_ROOT_PATH}/liveMedia/include)
include_directories(${LIVE555_ROOT_PATH}/UsageEnvironment/include)

file(GLOB BasicUsageEnvironmentFiles ${LIVE555_ROOT_PATH}/BasicUsageEnvironment/*.cpp)
#add_library(BasicUsageEnvironment SHARED ${BasicUsageEnvironmentFiles})

file(GLOB groupsockfiles ${LIVE555_ROOT_PATH}/groupsock/*.cpp ${LIVE555_ROOT_PATH}/groupsock/*.c)
#add_library(groupsock SHARED ${groupsockfiles})

file(GLOB liveMediaFiles ${LIVE555_ROOT_PATH}/liveMedia/*.hh ${LIVE555_ROOT_PATH}/liveMedia/*.cpp ${LIVE555_ROOT_PATH}/liveMedia/*.c)
#add_library(liveMedia SHARED ${liveMediaFiles})

file(GLOB UsageEnvironmentFiles ${LIVE555_ROOT_PATH}/UsageEnvironment/*.cpp ${LIVE555_ROOT_PATH}/UsageEnvironment/include/*.hh)
#add_library(UsageEnvironment SHARED ${UsageEnvironmentFiles})

file(GLOB mediaServerFiles ${LIVE555_ROOT_PATH}/mediaServer/*.hh ${LIVE555_ROOT_PATH}/mediaServer/*.cpp)
#add_library(mediaServer SHARED ${mediaServerFiles})

file(GLOB testProgsFiles ${LIVE555_ROOT_PATH}/testProgs/*.hh ${LIVE555_ROOT_PATH}/testProgs/*.cpp)
#add_library(testProgs SHARED ${testProgsFiles})

file(GLOB testFiles ${LIVE555_ROOT_PATH}/../*.cpp)
add_library(liblive555 SHARED ${groupsockfiles} ${testFiles} ${mediaServerFiles} ${BasicUsageEnvironmentFiles} ${UsageEnvironmentFiles}
${liveMediaFiles} )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because system libraries are included in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in the
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       liblive555
                       #BasicUsageEnvironment
                       #groupsockfiles
                       #liveMedia
                       #UsageEnvironment
                       #mediaServer
                      # testProgs

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )

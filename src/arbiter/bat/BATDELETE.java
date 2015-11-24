package arbiter.bat;

import java.io.Serializable;

public enum BATDELETE implements BATABLE, Serializable
{
	
	NONE	(null, true),	
	BINARY	(new String[]{"Obj","Plugins\\Obj"}, false),
	MS		(new String[]{"Obj\\MS_Release","Plugins\\Obj\\MS_Release"}, false),
	INTEL	(new String[]{"Obj\\INTEL_Release","Plugins\\Obj\\INTEL_Release"}, false),
	JAVA	(new String[]{"Obj\\classes","Plugins\\Obj\\classes"}, false),
	SRC		(
			
			
			new String[]{
					// Src
					".cvsignore",
					".metadata",
					"ApplicationSettings.h",
					"arbiter.keystore",
					"Arbiter_Test.sln",
					"build.xml",
					"build_cpp.xml",
					"Cnc",
					"convert_to_MSVC_compiler.cmd",
					"Curve",
					"Curve_Test",
					"DistanceSolver",
					"DXF",
					"DXF_Test",
					"Equation",
					"Geometry",
					"Globals.h",
					"GrindingGeometryTest",
					"JavaApplication",
					"Kinematics",
					"LaunchSettings.h",
					"LinAlg",
					"LinAlg_Test",
					"Loader",
					"MachineControlLibrary",
					"MachineControlLibrary_Test",
					"MachineModel",
					"MachineModel_Test",
					"MachineMove",
					"MachiningDesign",
					"MachiningDesign_Test",
					"MiscMath",
					"MiscMath_Test",
					"ModelView",
					"MoveContact",
					"MoveNcDesign",
					"ParametricFramework",
					"ParametricSystem",
					"PostProcess3D",
					"ProbingDesign",
					"Profile",
					"Proguard_common_config_file.pro",
					"Proguard_config_file.pro",
					"Proguard_mapping.txt",
					"Properties",
					"qtest.bat",
					"qtest_INTEL.bat",
					"Registry",
					"Reposition",
					"Reposition_Test",
					"resource.h",
					"Separation",
					"Silhouette",
					"Socket",
					"StreamFormatType.h",
					"SweptVolume",
					"SweptVolume_Test",
					"Test",
					"TestSupport",
					"ToolStudio",
					"ToolStudio.sln",
					"ToolStudioDeprecated",
					"ToolStudioDll",
					"ToolStudioLauncher",
					"ToolStudioTest",
					"ToolStudioTestSuite",
					"ToolStudioView",
					"ToolStudio_v10.sln",
					"TriangleSet.h",
					"UIEventType.h",
					"UnitTestUtility",
					"Utility",
					"VRML",
					"WwmInterface",
					
					// Plugins
					"Plugins\\.cvsignore",
					"Plugins\\archive_src.cmd",
					"Plugins\\build.xml",
					"Plugins\\build_cpp.xml",
					"Plugins\\DistanceSolverB",
					"Plugins\\GeometryPlugin",
					"Plugins\\IndexedSet",
					"Plugins\\Java",
					"Plugins\\Kinematics",
					"Plugins\\Launchers",
					"Plugins\\out.txt",
					"Plugins\\Plugin",
					"Plugins\\Plugins.sln",
					"Plugins\\PluginTest",
					"Plugins\\Proguard_config_file.pro",
					"Plugins\\Simulation3DApp",
					"Plugins\\Simulation3DDll",
					"Plugins\\Simulation3DTest",
					"Plugins\\SimulationDll",
					"Plugins\\Simulator",
					"Plugins\\SoftwareLicense",
					"Plugins\\SoftwareProtection",
					"Plugins\\SweptVolume",
					"Plugins\\UIEventExtraType.h",
					
			},
			
			
			
			false),
	ALL		(new String[]{""}, false);

	

	
	public final String[] dirs;
	public final boolean isNull;
	BATDELETE(String[] dirs, boolean isNull)
	{
		this.dirs = dirs;
		this.isNull = isNull;
	}
	
	public final BAT bat = BAT.DELETE;
	
	
	@Override
	public boolean isNull() {
		return isNull;
	}
	
}

package com.treefrogapps.desktop.linux.sensor.monitor.data

import com.treefrogapps.kotlin.core.extensions.orElse
import java.util.function.Function
import javax.inject.Inject

class CpuFrequencyDataParser @Inject constructor() : Function<String, CpuFrequencyData> {

    companion object {
        private const val DELIMITER: String = ":"
        private const val NAME: String = "Model name:"
        private const val MHZ: String = "CPU MHz:"
        private const val MAX_MHZ = "CPU max MHz:"
        private const val MIN_MHZ = "CPU min MHz:"

        @JvmStatic private fun String.getCpuValue(): String = this.split(DELIMITER).getOrElse(1) { "" }.trim()
        @JvmStatic private fun String.getCpuGhzValue(): Double = getCpuValue().toDoubleOrNull()?.run { this / 1000 }.orElse { 0.0 }
    }

    override fun apply(p0: String): CpuFrequencyData =
        p0.split("\n").iterator().run {
            var data = CpuFrequencyData()
            while (hasNext()) {
                next().run {
                    when {
                        startsWith(NAME)    -> data = data.copy(name = getCpuValue())
                        startsWith(MHZ)     -> data = data.copy(currentGhz = getCpuGhzValue())
                        startsWith(MAX_MHZ) -> data = data.copy(maxGhz = getCpuGhzValue())
                        startsWith(MIN_MHZ) -> return data.copy(minGhz = getCpuGhzValue());
                    }
                }
            }
            data
        }
}

/*

Example "lscpu" data dump :

Architecture:                    x86_64
CPU op-mode(s):                  32-bit, 64-bit
Byte Order:                      Little Endian
Address sizes:                   39 bits physical, 48 bits virtual
CPU(s):                          16
On-line CPU(s) list:             0-15
Thread(s) per core:              2
Core(s) per socket:              8
Socket(s):                       1
NUMA node(s):                    1
Vendor ID:                       GenuineIntel
CPU family:                      6
Model:                           165
Model name:                      Intel(R) Core(TM) i7-10875H CPU @ 2.30GHz
Stepping:                        2
CPU MHz:                         900.139
CPU max MHz:                     5100.0000
CPU min MHz:                     800.0000
BogoMIPS:                        4599.93
Virtualisation:                  VT-x
L1d cache:                       256 KiB
L1i cache:                       256 KiB
L2 cache:                        2 MiB
L3 cache:                        16 MiB
NUMA node0 CPU(s):               0-15
Vulnerability Itlb multihit:     KVM: Mitigation: VMX disabled
Vulnerability L1tf:              Not affected
Vulnerability Mds:               Not affected
Vulnerability Meltdown:          Not affected
Vulnerability Spec store bypass: Mitigation; Speculative Store Bypass disabled v
                                 ia prctl and seccomp
Vulnerability Spectre v1:        Mitigation; usercopy/swapgs barriers and __user
                                  pointer sanitization
Vulnerability Spectre v2:        Mitigation; Enhanced IBRS, IBPB conditional, RS
                                 B filling
Vulnerability Srbds:             Not affected
Vulnerability Tsx async abort:   Not affected
Flags:                           fpu vme de pse tsc msr pae mce cx8 apic sep mtr
                                 r pge mca cmov pat pse36 clflush dts acpi mmx f
                                 xsr sse sse2 ss ht tm pbe syscall nx pdpe1gb rd
                                 tscp lm constant_tsc art arch_perfmon pebs bts
                                 rep_good nopl xtopology nonstop_tsc cpuid aperf
                                 mperf pni pclmulqdq dtes64 monitor ds_cpl vmx s
                                 mx est tm2 ssse3 sdbg fma cx16 xtpr pdcm pcid s
                                 se4_1 sse4_2 x2apic movbe popcnt tsc_deadline_t
                                 imer aes xsave avx f16c rdrand lahf_lm abm 3dno
                                 wprefetch cpuid_fault epb invpcid_single ssbd i
                                 brs ibpb stibp ibrs_enhanced tpr_shadow vnmi fl
                                 expriority ept vpid ept_ad fsgsbase tsc_adjust
                                 bmi1 avx2 smep bmi2 erms invpcid mpx rdseed adx
                                  smap clflushopt intel_pt xsaveopt xsavec xgetb
                                 v1 xsaves dtherm ida arat pln pts hwp hwp_notif
                                 y hwp_act_window hwp_epp pku ospke md_clear flu
                                 sh_l1d arch_capabilities
 */